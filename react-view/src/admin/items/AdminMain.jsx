import {Container, NavItem, NavLink} from "reactstrap";
import React from "react";
import {domain} from "../../constant/Constant";


const AdminMain = () => {
  return (
    <React.Fragment>
      <Container>
        <h1>관리자 페이지</h1>
        <NavItem>
          <NavLink href={"/admin/items"}>아이템 관리 바로가기</NavLink>
        </NavItem>
        <NavItem>
          <NavLink href={domain + "/admin/item/categories/manage"}>카테고리 관리 바로가기</NavLink>
        </NavItem>
        <NavItem>
          <NavLink href={"/admin/event"}>이벤트 관리 바로가기</NavLink>
        </NavItem>
      </Container>
    </React.Fragment>
  )
}

export default AdminMain;