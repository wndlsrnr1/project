import React, {useContext, useEffect, useState} from "react";
import {
  Button,
  ButtonGroup,
  Container,
  Form,
  Input,
  ListGroup,
  ListGroupItem,
  ListGroupItemText,
  Modal, ModalBody, ModalFooter, ModalHeader
} from "reactstrap";
import {AiOutlineArrowUp} from "@react-icons/all-files/ai/AiOutlineArrowUp";
import {AiOutlineArrowDown} from "@react-icons/all-files/ai/AiOutlineArrowDown";
import {ModalContext} from "./Buttons";
import {OutdatedContext} from "./EventMain";
import itemList from "../items/ItemList";


const Items = () => {

  const [items, setItems] = useState([]);
  const [loaded, setLoaded] = useState(false);
  const [errors, setErrors] = useState({})
  const [priorities, setPriorities] = useState([]);
  const [toggle, setToggle] = useState(false);
  const {outdatedPres, setOutdatedPres} = useContext(OutdatedContext);

  function getItems(outdated, priority) {
    let path = "/admin/items/events";
    if (outdated) path += "?outdated=" + outdated;
    fetch(path, {
      method: "get",
    }).then(response => {
      if (!response.ok) {
        response.json().then(data => {
          setErrors(data.data.errors);
        });
        return;
      }
      response.json().then(data => {
        setItems(data.data)
        if (priority) {
          const initPriority = [];
          console.log(data.data);
          data.data.forEach((elem, index) => {
            initPriority.push({id: elem.id, priority: index + 1})
          });
          setPriorities(initPriority);
        }
        setLoaded(true);
      });
    });
  }

  useEffect(() => {
    if (loaded) return;
    getItems(false, true);
  }, [loaded]);

  useEffect(() => {
    if (!loaded) return;
    console.log("outdatedPres", outdatedPres);
    getItems(outdatedPres, true);
  }, [outdatedPres]);


  const onClickUpButton = (itemId) => {
    const priority = priorities.filter((elem) => parseInt(elem.id) === itemId)[0].priority;
    if (parseInt(priority) === 1) return;
    const prevPriorities = [...priorities];
    prevPriorities.forEach((elem, index) => {
      if (elem.priority === priority) {
        elem.priority -= 1;
        return;
      }
      if (elem.priority === priority - 1) {
        elem.priority += 1;
      }
    });
    setPriorities(prevPriorities);
  }

  const onClickDownButton = (itemId) => {
    const priority = priorities.filter((elem) => parseInt(elem.id) === itemId)[0].priority;
    if (parseInt(priority) === priorities.length) return;
    const prevPriorities = [...priorities];
    prevPriorities.forEach((elem, index) => {
      if (elem.priority === priority) {
        elem.priority += 1;
        return;
      }
      if (elem.priority === priority + 1) {
        elem.priority -= 1;
      }
    });
    console.log(priorities);
    setPriorities(prevPriorities);
  }

  const getItemBgColor = (outdated) => {
    return outdated ? "bg-light" : "";
  }

  const onSubmitForm = (event) => {
    event.preventDefault();
    fetch("/admin/items/events/update", {
      method: "post",
      headers: {'Content-Type': 'application/json'},
      body: JSON.stringify({priorityList: priorities}),
    }).then(response => {
      if (!response.ok) {
        console.error("전송 에러");
        return;
      }
      setToggle(true);
      //적용됨.
    });
  }

  const onClickCancel = () => {
    setToggle(false);
  }

  return (
    <>
      <Container className={"w-75"}>
        <Form onSubmit={onSubmitForm}>
          <ListGroup>
            {
              items.length !== 0 && priorities.length !== 0 ? priorities.sort((a, b) => {
                return a.priority - b.priority;
              }).map((p, index) => {
                console.log("p", p);
                const elem = items.filter((e) => p.id === e.id)[0];
                console.log("e", elem);
                return (
                  <ListGroupItem key={elem.id} color={"primary"}
                                 className={"d-flex justify-content-start align-items-center rounded-3 mb-2 " + getItemBgColor(outdatedPres)}>
                    <ButtonGroup className={"pe-3 "} style={{borderColor: "#"}}>
                      {/*<Input type={"hidden"} value={elem.priority} name={"priority"}/>*/}
                      <Button onClick={() => onClickUpButton(elem.id)}
                              className={"ps-0 pe-0 text-danger bg-transparent border-0 rounded-3"}>
                        <AiOutlineArrowUp/>
                      </Button>
                      <Button onClick={() => onClickDownButton(elem.id)}
                              className={"ps-0 pe-0 text-success bg-transparent border-0 rounded-3"}>
                        <AiOutlineArrowDown/>
                      </Button>
                    </ButtonGroup>
                    <ListGroupItemText id={"event_" + elem.id}
                                       className={"mb-0 text-decoration-none border-0 bg-transparent w-100 text-center"}
                                       tag={"button"}>
                      {elem.nameKor + "(" + elem.name + ")"}
                    </ListGroupItemText>
                  </ListGroupItem>
                )
              }) : null
            }
            <div className={"d-flex justify-content-end"}>
              <Button className={"bg-primary"} type={"submit"}>우선순위 적용</Button>
            </div>
          </ListGroup>
        </Form>
      </Container>
      <Modal isOpen={toggle}>
        <ModalHeader>전송결과</ModalHeader>
        <ModalBody>우선순위 변경에 성공했습니다</ModalBody>
        <ModalFooter><Button className={"bg-primary"} onClick={onClickCancel}>확인</Button></ModalFooter>
      </Modal>
    </>
  );
}

export default Items;