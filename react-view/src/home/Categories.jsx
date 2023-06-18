import React from "react";
import {DropdownItem, DropdownMenu, DropdownToggle, ListGroup, ListGroupItem, UncontrolledDropdown} from "reactstrap";
import ShowCategory from "./ShowCategory";

const Categories = () => {

  return (
    <ListGroup horizontal className={"w-100 mb-4"}>
      <ShowCategory/>
      <ListGroupItem className={"bg-primary text-white flex-grow-1 text-center"}
                     href="#"
                     tag="a"
      >
        예약상품
      </ListGroupItem>
      <ListGroupItem className={"bg-primary text-white flex-grow-1 text-center"}
                     href="#"
                     tag="a"
      >
        세일상품
      </ListGroupItem>
      <ListGroupItem className={"bg-primary text-white flex-grow-1 text-center"}
                     href="#"
                     tag="a"
      >
        신상품
      </ListGroupItem>
      <ListGroupItem className={"bg-primary text-white flex-grow-1 text-center"}
                     href="#"
                     tag="a"
      >
        기획전
      </ListGroupItem>
      <ListGroupItem className={"bg-primary text-white flex-grow-1 text-center"}
                     href="#"
                     tag="a"
      >
        닌텐도
      </ListGroupItem>
      <ListGroupItem className={"bg-primary text-white flex-grow-1 text-center"}
                     href="#"
                     tag="a"
      >
        Xbox
      </ListGroupItem>
      <ListGroupItem className={"bg-primary text-white flex-grow-1 text-center"}
                     href="#"
                     tag="a"
      >
        PlayStation®5
      </ListGroupItem>
    </ListGroup>
  )
}

export default Categories;