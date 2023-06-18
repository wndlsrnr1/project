import {
  Badge,
  Col,
  DropdownItem,
  DropdownMenu,
  DropdownToggle,
  ListGroup, ListGroupItem, NavLink,
  Tooltip,
  UncontrolledDropdown
} from "reactstrap";
import React, {useState} from "react";
import List from "reactstrap/es/List";
import {Link} from "react-router-dom";

const ShowCategory = () => {

  const [category1Toggle, setCategoryToggle] = useState(false);

  const toggle1 = () => setCategoryToggle(!category1Toggle);

  return (
    <>
      <UncontrolledDropdown className={"flex-grow-1"}>
        <DropdownToggle
          caret
          color="primary"
          className={"w-100 h-100 rounded-0 rounded-start"}
        >
          카테고리
        </DropdownToggle>

        <DropdownMenu dark>
          <DropdownItem header>
            상품
          </DropdownItem>
          <DropdownItem id={"category_1"}>
            Category - 1
          </DropdownItem>
          <Tooltip isOpen={category1Toggle} target={"category_1"}List placement={"right"} autohide={false} toggle={toggle1} style={{background: "transparent", padding: "5px"}} className={"p-0"}>
            <ListGroup className={"w-100"}>
              <ListGroupItem className={"text-start"}><Link href={"#"} className={"text-black text-decoration-none"}>주문내역</Link></ListGroupItem>
              <ListGroupItem className={"text-start"}><Link href={"#"} className={"text-black text-decoration-none"} >회원정보</Link></ListGroupItem>
              <ListGroupItem className={"text-start"}><Link href={"#"} className={"text-black text-decoration-none"} >관심상품</Link></ListGroupItem>
              <ListGroupItem className={"text-start"}><Link href={"#"} className={"text-black text-decoration-none"} >적립금</Link></ListGroupItem>
              <ListGroupItem className={"text-start"}><Link href={"#"} className={"text-black text-decoration-none"} >게시물관리</Link></ListGroupItem>
              <ListGroupItem className={"text-start"}><Link href={"#"} className={"text-black text-decoration-none"} >배송주소록 관리</Link></ListGroupItem>
            </ListGroup>
          </Tooltip>
          <DropdownItem  text>
            Category - 2
          </DropdownItem>
          <DropdownItem divider/>
          <DropdownItem>
            Category - 3
          </DropdownItem>
          <DropdownItem>
            Category - 4
          </DropdownItem>
          <DropdownItem>
            Category - 5
          </DropdownItem>
        </DropdownMenu>
      </UncontrolledDropdown>

    </>

  )
}

export default ShowCategory;
