import React from "react";
import {Button, Input, InputGroup} from "reactstrap";

const HomeSearch = () => {

  return (
    <InputGroup>
      <Input type={"text"} style={{marginTop: "50px", maxWidth: "700px"}}/>
      <Button style={{background: "#e7e7e7"}} className={"pt-1"} style={{marginTop: "50px"}}><img alt={"search"} src={"/images/search.svg"}/></Button>
    </InputGroup>
  )
}

export default HomeSearch;