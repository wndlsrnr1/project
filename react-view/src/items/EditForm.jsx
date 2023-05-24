import React, {useContext, useState, useCallback, useEffect} from "react";
import {
  Alert,
  Button,
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
import {editFormObjDefault} from "../constant/Constant";

const errorDefault = {
  hasError: false,
  name: false,
  nameKor: false,
  price: false,
  quantity: false,
  brandId: false,
  categoryId: false,
  subcategoryId: false,
  nameMessage: "",
  nameKorMessage: "",
  priceMessage: "",
  quantityMessage: "",
  brandIdMessage: "",
  categoryIdMessage: "",
  subcategoryIdMessage: ""
}


const EditForm = () => {

  const {dispatch, editModal, searchState, editItemId, page} = useContext(ItemsContext);
  const [brands, setBrands] = useState([]);
  const [subcategories, setSubcategories] = useState({subcategoryList: []});
  const [categories, setCategories] = useState([]);
  const [itemData, setItemData] = useState(editFormObjDefault);
  const [errors, setErrors] = useState(errorDefault);

  const errorCheck = (data) => {
    const errors = {...errorDefault};

    Object.keys(data).forEach((key, index) => {
      let value = data[key];
      if (value == null || !value || value === -1) {
        errors.hasError = true;
        errors[key] = true;
        errors[key + "Message"] = key + " can not be null";
        return;
      }

      if (key === "price" || key === "quantity") {
        if (Number.isNaN(value)) {
          errors.hasError = true;
          errors[key] = true;
          errors[key + "Message"] = key + " can not be string";
          return;
        }

        if (parseInt(value) < 0) {
          errors.hasError = true;
          errors[key] = true;
          errors[key + "Message"] = key + " can not be less than 0";
        }
      }
    });

    return errors;
  }

  const onClickCancel = () => {
    dispatch({type: actionObj.editToggle, editModal: editModal});
    dispatch({type: actionObj.setEditItemId, editItemId: -1});
    setItemData(editFormObjDefault);
    setCategories([]);
    setBrands([]);
    setSubcategories([]);
    setErrors(errorDefault);
  }


  const onSubmitEditForm = (event) => {
    event.preventDefault();

    const errorObj = errorCheck(itemData);

    if (errorObj.hasError) {
      setErrors(errorObj);
      return;
    }

    let formData = new FormData(event.target);
    formData.append("id", itemData.id);
    //함수가 생성될때 itemData 값이 기억됨.
    fetch("/admin/items/edit", {
      method: "POST",
      body: formData,
    }).then(response => {
      if (response.ok) {
        dispatch({type: actionObj.changeSearchState, searchState: {...searchState}});
        dispatch({type: actionObj.editToggle, editModal: editModal});
        dispatch({type: actionObj.setEditItemId, editItemId: -1});
        setErrors(errorDefault);
        console.log("성공");
      } else {
        console.log("실패");
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

      console.log(subcategoriesData)
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

  return (
    <Modal isOpen={editModal} size={"lg"}>
      <Form onSubmit={onSubmitEditForm} action={"/admin/items/edit"} method={"post"}>
        <ModalHeader closeButton hideCloseButton={true} className={"text-xs"}>상품 수정</ModalHeader>
        <ModalBody className={"text-xxl"}>
          <FormGroup row className={"mb-3"}>
            <Label sm={2}>영어 이름</Label>
            <Col sm={10}>
              <Input name={"name"} value={itemData.name} onChange={onChangeInput}/>
              {errors.name ? <Alert sm={10} color={"danger"} size={"sm"}
                                    className={"mt-2 p-1 mb-0 ps-3"}>{errors.nameMessage}</Alert> : null}
            </Col>
          </FormGroup>
          <FormGroup row className={"mb-3"}>
            <Label sm={2}>한글 이름</Label>
            <Col sm={10}>
              <Input name={"nameKor"} value={itemData.nameKor} onChange={onChangeInput}/>
              {errors.nameKor ? <Alert sm={10} color={"danger"} size={"sm"}
                                       className={"mt-2 p-1 mb-0 ps-3"}>{errors.nameKorMessage}</Alert> : null}
            </Col>

          </FormGroup>
          <FormGroup row className={"mb-3"}>
            <Label sm={2}>가격</Label>
            <Col sm={10}>
              <Input name={"price"} type={"number"} value={itemData.price} onChange={onChangeInput}/>
              {errors.price ? <Alert sm={10} color={"danger"} size={"sm"}
                                     className={"mt-2 p-1 mb-0 ps-3"}>{errors.priceMessage}</Alert> : null}
            </Col>
          </FormGroup>
          <FormGroup row className={"mb-3"}>
            <Label sm={2}>수량</Label>
            <Col sm={10}>
              <Input name={"quantity"} type={"number"} value={itemData.quantity} onChange={onChangeInput}/>
              {errors.quantity ? <Alert sm={10} color={"danger"} size={"sm"}
                                        className={"mt-2 p-1 mb-0 ps-3"}>{errors.quantityMessage}</Alert> : null}
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