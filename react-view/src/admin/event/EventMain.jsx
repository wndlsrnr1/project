import React, {createContext, useState} from "react";
import {Container} from "reactstrap";
import Buttons from "./Buttons";
import Items from "./Items";

export const OutdatedContext = createContext();

const EventMain = () => {

  const [outdatedPres, setOutdatedPres] = useState(false);

  return (
    <>
      <OutdatedContext.Provider value={{outdatedPres, setOutdatedPres}}>
        <Container className={"w-75 text-center"}>
          <h2 className={"py-5"}>이벤트 관리</h2>
          <Buttons/>
          <Items/>
        </Container>
      </OutdatedContext.Provider>
    </>
  )
}

export default EventMain;