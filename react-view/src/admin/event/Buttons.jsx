import React, {useState, useContext} from "react";
import {Button, ButtonGroup, Container, ListGroup, ListGroupItem} from "reactstrap";
import {AiOutlinePlus} from "@react-icons/all-files/ai/AiOutlinePlus";
import AddEventForm from "./AddEventForm";
import {createContext} from "react";
import {OutdatedContext} from "./EventMain";

export const ModalContext = createContext();

const Buttons = () => {
  const [modalToggle, setModalToggle] = useState(false);
  const {outdatedPres, setOutdatedPres} = useContext(OutdatedContext);
  console.log("outdatedPres", outdatedPres);
  const openAddEventForm = () => {
    setModalToggle(true);
  }

  const outdatedPresToggle = () => {
    setOutdatedPres(!outdatedPres);
  }


  return (
    <ModalContext.Provider value={{modalToggle, setModalToggle}}>
      <Container horizontal={true} className={"d-flex justify-content-end mb-4 w-75"}>
        <Button className={"bg-primary me-2"} onClick={openAddEventForm}>
          <AiOutlinePlus className={"h-100"}/><span>이벤트 추가</span>
        </Button>
        {
          !outdatedPres
            ? <Button onClick={outdatedPresToggle}>종료 이벤트 보기</Button>
            : <Button onClick={outdatedPresToggle} className={"bg-primary"}>진행중인 이벤트 보기</Button>

        }
      </Container>
      <AddEventForm props={modalToggle}/>
    </ModalContext.Provider>
  )
}
export default Buttons;