import React from "react";
import {Col, Row} from "reactstrap";

const Footer = () => {
  return (
    <footer>
      <Row>
        <Col sm={4}>
          <div className={"p-3"} style={{height: "140px"}}>
            고객센터
          </div>
          <div className={"p-3"} style={{height: "95px"}}>
            입금계좌안내
          </div>
        </Col>
        <Col sm={4}>
          <div className={"p-3"} style={{height: "140px"}}>
            공지사항
          </div>
          <div className={"p-3"} style={{height: "95px"}}>
            고객센터, 마이페이지, 장바구니, 주문조회, 배송조회
          </div>
        </Col>
        <Col sm={4}>
          <div className={"p-3"} style={{height: "140px"}}>
            오프라인 매장안내
          </div>
          <div className={"p-3"} style={{height: "95px"}}>
            Q & A
          </div>
        </Col>
      </Row>
    </footer>
  )
}

export default Footer;