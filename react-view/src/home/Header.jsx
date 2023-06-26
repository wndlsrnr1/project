import React, {useContext, useState} from "react";
import {Badge, Col, Tooltip} from "reactstrap";
import {Link} from "react-router-dom";
import {domain} from "../constant/Constant";
import {UniversalContext} from "../App";

const Header = () => {

  const [myPageListToggle, setMyPageListToggle] = useState(false);
  const [communityListToggle, setCommunityListToggle] = useState(false);

  const toggle1 = () => setMyPageListToggle(!myPageListToggle);
  const toggle2 = () => setCommunityListToggle(!communityListToggle);

  const {dispatch, isLoggedIn} = useContext(UniversalContext);

  const linkList = {
    login: "/login",

  }
  console.log(isLoggedIn);
  const myPage = (isLoggedIn) => {
    if (isLoggedIn) {
      return "";
    }
    return domain + "/login"
  }
  return (
    <>

      <header className={"pe-lg-5 ps-lg-5 pb-lg-3 pt-lg-1 border-bottom mb-3 d-flex justify-content-between"}>
        {/*왼쪽*/}
        <div>
          <Badge color={"grey"} href={"#"} className={"text-secondary"}>즐겨찾기</Badge>
          <Badge color={"grey"} href={"#"} className={"text-secondary"}>PC 바로가기 아이콘</Badge>
        </div>
        {/*오른쪽*/}
        <div>
          {isLoggedIn ? null : <Badge color={"grey"} href={domain + "/login"} className={"text-secondary"}>로그인</Badge>}
          {isLoggedIn ? null : <Badge color={"grey"} href={domain + "/login/join"} className={"text-secondary"}>회원가입</Badge>}
          {isLoggedIn ? <Badge color={"grey"} href={"#"} className={"text-secondary"} id={"my-page"}>마이페이지</Badge> : <Badge color={"grey"} href={domain + "/login"} className={"text-secondary"} id={"my-page"}>마이페이지</Badge>}
          <Badge color={"grey"} href={"#"} className={"text-secondary"}>장바구니</Badge>
          <Badge color={"grey"} href={"#"} className={"text-secondary"}>주문조회</Badge>
          <Badge id={"community"} color={"grey"} href={"#"} className={"text-secondary"}>커뮤니티</Badge>
          <Tooltip isOpen={myPageListToggle} target={"my-page"} placement={"bottom"} autohide={false} toggle={toggle1} style={{background: "rgb(51, 51, 51)", }}>
            <Col className={"text-start"}><Badge href={"#"}>주문내역</Badge></Col>
            <Col className={"text-start"}><Badge href={"#"}>회원정보</Badge></Col>
            <Col className={"text-start"}><Badge href={"#"}>관심상품</Badge></Col>
            <Col className={"text-start"}><Badge href={"#"}>적립금</Badge></Col>
            <Col className={"text-start"}><Badge href={"#"}>게시물관리</Badge></Col>
            <Col className={"text-start"}><Badge href={"#"}>배송주소록 관리</Badge></Col>
          </Tooltip>
          <Tooltip isOpen={communityListToggle} target={"community"} placement={"bottom"} autohide={false} toggle={toggle2} style={{background: "rgb(51, 51, 51)", }}>
            <Col className={"text-start"}><Badge href={"#"}>게임뉴스</Badge></Col>
            <Col className={"text-start"}><Badge href={"#"}>공지</Badge></Col>
            <Col className={"text-start"}><Badge href={"#"}>FAQ</Badge></Col>
            <Col className={"text-start"}><Badge href={"#"}>상품 Q&A</Badge></Col>
            <Col className={"text-start"}><Badge href={"#"}>상품구매 후기</Badge></Col>
            <Col className={"text-start"}><Badge href={"#"}>질문 게시판</Badge></Col>
          </Tooltip>
        </div>
      </header>


    </>
  )
}

export default Header;