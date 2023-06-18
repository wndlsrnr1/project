import React, {useContext, useState, useCallback, useEffect} from "react";
import {
  Alert,
  Button, Card, CardImg, CardImgOverlay,
  Col,
  Form,
  FormGroup,
  Input,
  Label,
  Modal,
  ModalBody,
  ModalFooter,
  ModalHeader
} from "reactstrap";

import {actionObj, ItemsContext} from "./ItemsMain";
import {editFormObjDefault} from "../../constant/Constant";


const EditForm = () => {

  const {dispatch, editModal, searchState, editItemId, page} = useContext(ItemsContext);
  const [brands, setBrands] = useState([]);
  const [subcategories, setSubcategories] = useState({subcategoryList: []});
  const [categories, setCategories] = useState([]);
  const [itemData, setItemData] = useState(editFormObjDefault);
  const [imageList, setImageList] = useState([]);
  const [deleteImageList, setDeleteImageList] = useState([]);
  const [fieldError, setFieldError] = useState({});

  const resetValues = () => {
    dispatch({type: actionObj.setEditItemId, editItemId: -1});
    setItemData(editFormObjDefault);
    setCategories([]);
    setBrands([]);
    setSubcategories([]);
    setDeleteImageList([]);
    setImageList([]);
    setFieldError({});
  }

  const onClickCancel = () => {
    dispatch({type: actionObj.editToggle, editModal: editModal});
    resetValues();
  }

  const onSubmitEditForm = (event) => {
    event.preventDefault();

    let formData = new FormData(event.target);
    formData.append("id", itemData.id);
    formData.append("deleteImages", deleteImageList);

    //함수가 생성될때 itemData 값이 기억됨.
    fetch("/admin/items/edit", {
      method: "POST",
      body: formData,
    }).then(response => {
      if (response.ok) {
        dispatch({type: actionObj.changeSearchState, searchState: {...searchState}});
        dispatch({type: actionObj.editToggle, editModal: editModal});
        dispatch({type: actionObj.setEditItemId, editItemId: -1});
        console.log("성공");
        resetValues();

      } else if (response.status === 400) {
        response.json().then(data => {
          setFieldError(data.fieldErrors);
          console.log(data.fieldErrors);
        });
      }
    });
  }

  //modal initial data
  useEffect(() => {
    if (!editModal) {
      return;
    }

    if (!(editModal && editItemId !== -1)) {
      //editItem용 객체에 데이터 넣기
      return;
    }

    async function getEditData() {
      const itemData = await (await fetch("/admin/items/get/" + editItemId)).json();
      const brandData = await (await fetch("/admin/items/get/brands")).json();
      const categoriesData = await (await fetch("/admin/items/get/categories")).json();

      const imageNames = await (await fetch("/admin/items/attaches/" + editItemId).then(response => {
        if (response.ok) {
          return response.json();
        }
        return null;
      })
        .then(data => {
          if (data === null) {
            return [];
          }
          return data.data.imageList
        }));

      console.log("imageNames", imageNames);


      const subcategoriesData = await (await fetch("/admin/items/get/subcategory/" + itemData.categoryId)
          .then(response => {
            if (response.ok) {
              return response;
            }
            return {subcategoryList: []};
          })
      ).json();

      setBrands(brandData);
      setCategories(categoriesData);
      setSubcategories(subcategoriesData);
      setItemData(itemData);
      setImageList(imageNames);

    }

    getEditData();

  }, [editItemId, editModal])

  //select 변할때
  const onChangeSelect = (event) => {
    const value = event.target.value;
    const name = event.target.name;

    if (name === "categoryId") {
      //category 데이터 전송해서 subcategory 정보 받은 후 item의 subcategory 정보 초기화
      fetch("/admin/items/get/subcategory/" + value)
        .then(response => {
          if (response.ok) {
            return response.json();
          }
          return {subcategoryList: []}
        })
        .then(data => {
          if (data.subcategoryList.length === 0) {
            setItemData({...itemData, categoryId: value, subcategoryId: -1});
          } else {
            setItemData({...itemData, categoryId: value, subcategoryId: data.subcategoryList[0]});
          }
          setSubcategories(data);
        });
      return;
    }

    const itemDataObj = {...itemData};
    itemDataObj[name] = value;
    setItemData(itemDataObj);
  }

  const onChangeInput = (event) => {
    const value = event.target.value;
    const name = event.target.name;
    const updateData = {...itemData};
    updateData[name] = value;
    setItemData(updateData);
  };

  const onClickDeleteImage = (name) => {
    const updatedImageList = imageList.filter(elem => {
      if (elem.uuid !== name) {
        return true
      }
    });

    setDeleteImageList([...deleteImageList, name]);
    setImageList([...updatedImageList]);
  }

  const limitFileMax = (event) => {
    const images = event.target.files;
    if (images.length > 3) {
      event.target.value = '';
    }
  }

return (
  <Modal isOpen={editModal} size={"lg"}>
    <Form onSubmit={onSubmitEditForm} action={"/admin/items/edit"} method={"post"} encType={"multipart/form-data"}>
      <ModalHeader closeButton hideCloseButton={true} className={"text-xs"}>상품 수정</ModalHeader>
      <ModalBody className={"text-xxl"}>
        <FormGroup row className={"mb-3"}>
          <Label sm={2}>영어 이름</Label>
          <Col sm={10}>
            <Input name={"name"} value={itemData.name} onChange={onChangeInput}/>
            {fieldError.name ? <Alert sm={10} color={"danger"} size={"sm"}
                                  className={"mt-2 p-1 mb-0 ps-3"}>{fieldError.name}</Alert> : null}
          </Col>
        </FormGroup>
        <FormGroup row className={"mb-3"}>
          <Label sm={2}>한글 이름</Label>
          <Col sm={10}>
            <Input name={"nameKor"} value={itemData.nameKor} onChange={onChangeInput}/>
            {fieldError.nameKor ? <Alert sm={10} color={"danger"} size={"sm"}
                                      className={"mt-2 p-1 mb-0 ps-3"}>{fieldError.nameKor}</Alert> : null}
          </Col>

        </FormGroup>
        <FormGroup row className={"mb-3"}>
          <Label sm={2}>가격</Label>
          <Col sm={10}>
            <Input name={"price"} type={"number"} value={itemData.price} onChange={onChangeInput}/>
            {fieldError.price ? <Alert sm={10} color={"danger"} size={"sm"}
                                         className={"mt-2 p-1 mb-0 ps-3"}>{fieldError.price}</Alert> : null}
          </Col>
        </FormGroup>
        <FormGroup row className={"mb-3"}>
          <Label sm={2}>수량</Label>
          <Col sm={10}>
            <Input name={"quantity"} type={"number"} value={itemData.quantity} onChange={onChangeInput}/>
            {fieldError.quantity ? <Alert sm={10} color={"danger"} size={"sm"}
                                       className={"mt-2 p-1 mb-0 ps-3"}>{fieldError.quantity}</Alert> : null}
          </Col>

        </FormGroup>
        <FormGroup row className={"mb-3"}>
          <Label sm={2}>브랜드</Label>
          <Col sm={10}>
            <Input type={"select"} name={"brandId"} value={itemData.brandId} onChange={onChangeSelect}>
              {
                brands.length !== 0 ? brands.map(brand => {
                  return <option value={brand.id} name={"brandId"}>{brand.nameKor}</option>
                }) : <option>선택</option>
              }
            </Input>
          </Col>
        </FormGroup>
        <FormGroup row className={"mb-3"}>
          <Label sm={2}>카테고리</Label>
          <Col sm={10}>
            <Input type={"select"} name={"categoryId"} onChange={onChangeSelect} value={itemData.categoryId}>
              {
                categories.length !== 0 ? categories.map(category => {
                  return <option value={category.id}>{category.nameKor}</option>
                }) : <option>선택</option>
              }
            </Input>
          </Col>
        </FormGroup>
        <FormGroup row className={"mb-3"}>
          <Label sm={2}>서브 카테고리</Label>
          <Col sm={10}>
            <Input type={"select"} name={"subcategoryId"} value={itemData.subcategoryId} onChange={onChangeSelect}>
              {
                Object.keys(subcategories).length !== 0 && subcategories.subcategoryList.length !== 0 ? subcategories.subcategoryList.map(subcategory => {
                  return <option value={subcategory.id}>{subcategory.nameKor}</option>
                }) : <option value={""}>없음</option>
              }
            </Input>
          </Col>
        </FormGroup>
        <div style={{display: "flex"}} className={"mb-3"}>
          {
            imageList.map(image => {
              console.log(image);
              return (
                <Card style={{height: "150px", maxWidth: "200px", alignItems: "center"}}>
                  <CardImg src={"/admin/items/images/" + image.uuid} style={{height: "100%"}}/>
                  <CardImgOverlay className={"p-0"}>
                    <button onClick={() => onClickDeleteImage(image.uuid)}
                            type={"button"}
                            className={"p-0 text-center bg-transparent text-black-50 border-0 font-weight-bold"}
                            style={{float: "right", height: "30px", width: "30px", fontWeight: "bold"}}
                    >X
                    </button>
                  </CardImgOverlay>
                </Card>
              )

            })
          }
        </div>
        <FormGroup>
          <Label sm={2}>이미지 업로드</Label>
          <Input
            id={"image_upload"} name={"images"} type={"file"} accept={"image/png, image/jpeg"} multiple={true} onChange={limitFileMax}/>
        </FormGroup>

      </ModalBody>
      <ModalFooter>
        <Button type={"submit"} color={"primary"}>확인</Button>
        <Button color={"secondary"} type={"button"} onClick={onClickCancel}>취소</Button>
      </ModalFooter>
    </Form>
  </Modal>
);

}

export default EditForm;