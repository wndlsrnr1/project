import React, {useContext, useEffect, useState} from "react";
import {Button, ButtonGroup, Container, ListGroup, ListGroupItem, ListGroupItemText} from "reactstrap";
import {AiOutlineArrowUp} from "@react-icons/all-files/ai/AiOutlineArrowUp";
import {AiOutlineArrowDown} from "@react-icons/all-files/ai/AiOutlineArrowDown";
import {ModalContext} from "./Buttons";
import {OutdatedContext} from "./EventMain";



const Items = () => {

  const [items, setItems] = useState([]);
  const [loaded, setLoaded] = useState(false);
  const [errors, setErrors] = useState({})
  const {outdatedPres, setOutdatedPres} = useContext(OutdatedContext);

  function getItems(outdated) {
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
        setLoaded(true);
      });
    });
  }

  useEffect(() => {
    if (loaded) return;
    getItems();
  }, [loaded]);

  useEffect(() => {
    if (!loaded) return;
    console.log("outdatedPres", outdatedPres);
    getItems(outdatedPres);
  }, [outdatedPres]);


  const onClickUpButton = () => {

  }

  const onClickDownButton = () => {

  }

  const getItemBgColor = (outdated) => {
    return outdated ? "bg-light" : "";
  }

  return (
    <Container className={"w-75"}>
      <ListGroup>
        {
          items.length !== 0 ? items.map((elem, index) => {
            return (
              <ListGroupItem color={"primary"}
                             className={"d-flex justify-content-start align-items-center rounded-3 mb-2 " + getItemBgColor(outdatedPres)}>
                <ButtonGroup className={"pe-3 "} style={{borderColor: "#"}}>
                  <Button onClick={onClickUpButton}
                          className={"ps-0 pe-0 text-danger bg-transparent border-0 rounded-3"}>
                    <AiOutlineArrowUp/>
                  </Button>
                  <Button onClick={onClickDownButton}
                          className={"ps-0 pe-0 text-success bg-transparent border-0 rounded-3"}>
                    <AiOutlineArrowDown/>
                  </Button>
                </ButtonGroup>
                <ListGroupItemText id={"event_" + elem.id} className={"mb-0 text-decoration-none border-0 bg-transparent w-100 text-center"}
                                   tag={"button"}>
                  {elem.nameKor + "(" + elem.name + ")"}
                </ListGroupItemText>
              </ListGroupItem>
            )
          }) : null
        }
      </ListGroup>
    </Container>
  );
}

export default Items;