import React, {memo, useCallback, useContext} from "react";
import {Button, Col, Modal, ModalBody, ModalHeader, Row, ModalFooter, FormGroup, Input, Label, Form} from "reactstrap";
import {actionObj, ItemsContext} from "./ItemsMain";

const Buttons = memo(() => {

  const {
    dispatch,
    itemList,
    load,
    itemSelected,
    brand,
    subcategory,
    category,
    price,
    quantity,
    nameKor,
    modal,
    addModal
  } = useContext(ItemsContext);

  const onClickDelete = () => {
    return null
  }

  const onClickAdd = () => {
    return null;
  }

  const warningToggle = useCallback(() => {
      dispatch({type: actionObj.warningToggle, modal: modal});
    }, [modal]
  );

  return (
    <Row>
      <Col>
        <Button className={"btn btn-secondary float-end"} onClick={warningToggle}>삭제</Button>
        <Modal isOpen={modal}>
          <ModalHeader closeButton hideCloseButton={true} className={"text-xs-start"}>삭제경고</ModalHeader>
          <ModalBody className={"text-xxl"}>
            정말 데이터를 지우시겠습니까?
          </ModalBody>
          <ModalFooter>
            <Button color="primary" onClick={warningToggle}>
              확인
            </Button>{' '}
            <Button color="secondary" onClick={warningToggle}>
              취소
            </Button>
          </ModalFooter>
        </Modal>
      </Col>
    </Row>
  )
});

export default Buttons;