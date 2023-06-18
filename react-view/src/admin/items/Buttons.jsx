import React, {memo, useCallback, useContext} from "react";
import {Button, Col, Modal, ModalBody, ModalHeader, Row, ModalFooter, FormGroup, Input, Label, Form} from "reactstrap";
import {actionObj, ItemsContext} from "./ItemsMain";
import itemList from "./ItemList";

const Buttons = memo(() => {

  const {
    dispatch,
    itemList,
    load,
    modal,
    addModal,
    searchState,
    itemSelected
  } = useContext(ItemsContext);

  const onClickDelete = () => {
    const itemIdList = Object.values(itemSelected);

    fetch("/admin/items/delete", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(itemIdList),
    }).then(response => {
      if (response.ok) {
        warningToggle();
        dispatch({type: actionObj.changeSearchState, searchState: {...searchState}});
      } else {
        warningToggle();
      }
    });
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
            {/*data 없을때 뜨지 않음.*/}
            정말 데이터를 지우시겠습니까?
          </ModalBody>
          <ModalFooter>
            <Button color="primary" onClick={onClickDelete}>확인</Button>{' '}
            <Button color="secondary" onClick={warningToggle}>취소</Button>
          </ModalFooter>
        </Modal>
      </Col>
    </Row>
  )
});

export default Buttons;