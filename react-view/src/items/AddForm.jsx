import {Button, Col, Form, FormGroup, Input, Label, Modal, ModalBody, ModalFooter, ModalHeader, Row} from "reactstrap";
import React, {useContext, useCallback} from "react";
import {actionObj, ItemsContext} from "./ItemsMain";


const AddForm = () => {

  const {addModal, dispatch} = useContext(ItemsContext);

  const addToggle = useCallback(() => {
    console.log(addModal);
    dispatch({type: actionObj.addToggle, addModal: addModal});
  }, [addModal])

  return (
    <Row>
      <Col>
        <Button color={"primary"} className={"btn float-end"} type={"button"} onClick={addToggle}>상품 등록</Button>
      </Col>
      <Modal isOpen={addModal} size={"lg"}>
        <Form>
          <ModalHeader closeButton hideCloseBUtton={true} className={"text-xs"}>추가</ModalHeader>
          <ModalBody className={"text-xxl"}>
            <FormGroup row className={"mb-3"}>
              <Label sm={2}>영어 이름</Label>
              <Col sm={10}><Input/></Col>
            </FormGroup>
            <FormGroup row className={"mb-3"}>
              <Label sm={2}>한글 이름</Label>
              <Col sm={10}><Input/></Col>
            </FormGroup>
            <FormGroup row className={"mb-3"}>
              <Label sm={2}>가격</Label>
              <Col sm={10}><Input type={"number"}/></Col>
            </FormGroup>
            <FormGroup row className={"mb-3"}>
              <Label sm={2}>수량</Label>
              <Col sm={10}><Input type={"number"}/></Col>
            </FormGroup>
            <FormGroup row className={"mb-3"}>
              <Label sm={2}>브랜드</Label>
              <Col sm={10}>
                <Input type={"select"} name={"quantity"}>
                  <option>Sony</option>
                  <option>Xbox</option>
                </Input>
              </Col>
            </FormGroup>
            <FormGroup row className={"mb-3"}>
              <Label sm={2}>카테고리</Label>
              <Col sm={10}>
                <Input type={"select"} name={"category"}>
                  <option>본체</option>
                  <option>게임 타이틀</option>
                </Input>
              </Col>
            </FormGroup>
            <FormGroup row className={"mb-3"}>
              <Label sm={2}>서브 카테고리</Label>
              <Col sm={10}>
                <Input type={"select"} name={"category"}>
                  <option>본체의 서브카테고리</option>
                  <option>타이틀의 서브 카테고리</option>
                </Input>
              </Col>
            </FormGroup>
          </ModalBody>
          <ModalFooter>
            <Button color={"primary"} onClick={addToggle}>확인</Button>
            <Button color={"secondary"} onClick={addToggle}>취소</Button>
          </ModalFooter>
        </Form>
      </Modal>
    </Row>
  )
}

export default AddForm;