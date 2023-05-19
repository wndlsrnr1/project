import { Button, Col, Form, FormGroup, Input, Label, Modal, ModalBody, ModalFooter, ModalHeader, Row, Alert} from "reactstrap";
import React, {useContext, useCallback, useState, useEffect} from "react";
import {actionObj, ItemsContext} from "./ItemsMain";

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

const AddForm = () => {

  const {addModal, dispatch} = useContext(ItemsContext);
  const [brands, setBrands] = useState([]);
  const [subcategories, setSubcategories] = useState({subcategoryList: []});
  const [categories, setCategories] = useState([]);
  const [errors, setErrors] = useState(errorDefault);

  //get Initial Data
  useEffect(() => {
    if (!addModal) {
      return;
    }

    async function fetchData() {
      const response1 = await fetch("/admin/items/get/brands");
      const response2 = await fetch("/admin/items/get/categories");
      if (!response1.ok || !response2.ok) {
        return;
      }
      const brandsJson = await response1.json();
      const categoriesJson = await response2.json();
      setBrands(brandsJson);
      setCategories(categoriesJson);
    }

    fetchData();

  }, [addModal]);

  useEffect(() => {
    if (brands.length !== 0 && categories.length !== 0) {

    }
  }, [brands, categories])

  const addToggle = useCallback(() => {
    setErrors(errorDefault);
    dispatch({type: actionObj.addToggle, addModal: addModal});
  }, [addModal, errors])

  const errorCheck = (formData) => {
    const errors = {...errorDefault};

    Array.from(formData.keys()).forEach(key => {
      //null check
      if (!formData.get(key) && key !== "subcategoryId") {
        errors.hasError = true;
        errors[key] = true;
        errors[key + "Message"] = key + " can not be null";
        return;
      }

      if (key === "price" || key === "quantity") {
        if (Number.isNaN(formData.get(key))) {
          errors.hasError = true;
          errors[key] = true;
          errors[key + "Message"] = key + " can not be string";
          return;
        }
        if (parseInt(formData.get(key)) < 0) {
          errors.hasError = true;
          errors[key] = true;
          errors[key + "Message"] = key + " can not be less than 0";
        }
      }
    });

    return errors;
  }

  //
  const onSubmitAddForm = (event) => {
    event.preventDefault();
    const formData = new FormData(event.target);

    const errorsCreated = errorCheck(formData);

    //에러가 있으면
    if (errorsCreated.hasError) {
      setErrors(errorsCreated);
      return;
    }

    fetch("/admin/items/add", {
      method: "POST",
      body: formData
    }).then((response) => {
      if (!response.ok) {
        console.log("상품 추가에 실패했습니다.");
      }
    });
    //data 초기화
    setErrors(errorDefault)
    setBrands([]);
    setCategories([]);
    setBrands([]);

    dispatch({type: actionObj.updatePage});
    dispatch({type: actionObj.addToggle, addModal: addModal});
  }

  const onChangeCategory = useCallback((event) => {
    const categoryId = event.target.value;
    fetch("/admin/items/get/subcategory/" + categoryId)
      .then(response => {
        if (response.ok) {
          return response.json();
        }
        return {subcategoryList: []}
      })
      .then(data => {
        setSubcategories(data);
      });

  }, [categories]);

  return (
    <Row>
      <Col>
        <Button color={"primary"} className={"btn float-end"} type={"button"} onClick={addToggle}>상품 등록</Button>
      </Col>
      <Modal isOpen={addModal} size={"lg"}>
        <Form onSubmit={onSubmitAddForm} action={"/admin/items/add"} method={"post"}>
          <ModalHeader closeButton hideCloseBUtton={true} className={"text-xs"}>상품 추가</ModalHeader>
          <ModalBody className={"text-xxl"}>
            <FormGroup row className={"mb-3"}>
              <Label sm={2}>영어 이름</Label>
              <Col sm={10}>
                <Input name={"name"}/>
                {errors.name ? <Alert sm={10} color={"danger"} size={"sm"} className={"mt-2 p-1 mb-0 ps-3"}>{errors.nameMessage}</Alert> : null}
              </Col>
            </FormGroup>
            <FormGroup row className={"mb-3"}>
              <Label sm={2}>한글 이름</Label>
              <Col sm={10}>
                <Input name={"nameKor"}/>
                {errors.nameKor ? <Alert sm={10} color={"danger"} size={"sm"} className={"mt-2 p-1 mb-0 ps-3"}>{errors.nameKorMessage}</Alert> : null}
              </Col>

            </FormGroup>
            <FormGroup row className={"mb-3"}>
              <Label sm={2}>가격</Label>
              <Col sm={10}>
                <Input name={"price"} type={"number"}/>
                {errors.price ? <Alert sm={10} color={"danger"} size={"sm"} className={"mt-2 p-1 mb-0 ps-3"}>{errors.priceMessage}</Alert> : null}
              </Col>
            </FormGroup>
            <FormGroup row className={"mb-3"}>
              <Label sm={2}>수량</Label>
              <Col sm={10}>
                <Input name={"quantity"} type={"number"}/>
                {errors.quantity ? <Alert sm={10} color={"danger"} size={"sm"} className={"mt-2 p-1 mb-0 ps-3"}>{errors.quantityMessage}</Alert> : null}
              </Col>

            </FormGroup>
            <FormGroup row className={"mb-3"}>
              <Label sm={2}>브랜드</Label>
              <Col sm={10}>
                <Input type={"select"} name={"brandId"}>
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
                <Input type={"select"} name={"categoryId"} onChange={onChangeCategory}>
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
                <Input type={"select"} name={"subcategoryId"}>
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
            <Button type={"submit"} color={"primary"} >확인</Button>
            <Button color={"secondary"} onClick={addToggle}>취소</Button>
          </ModalFooter>
        </Form>
      </Modal>
    </Row>
  );
}

export default AddForm;