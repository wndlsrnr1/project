import React from "react";
import {Col, ListGroup, ListGroupItem, Row} from "reactstrap";

const Items = () => {

  return (
    <div>
      <h3>신상품</h3>
      <Row>
        {new Array(7).fill().map((elem, index) => {
          return (
            <Col sm={2} style={{height: "140px"}}>
              <a className={"w-100"}>
                <img alt={"?"} src={"/images/logo.jpg"} className={"w-100"}/>
              </a>
              <span>게임 설명</span>
              <hr/>
              <ListGroup>
                <ListGroupItem className={"border-0 small p-0 text-sm-center"}>48,000</ListGroupItem>
                <ListGroupItem className={"border-0 small p-0 text-sm-center"}>50,000</ListGroupItem>
                <ListGroupItem className={"border-0 small p-0 text-sm-center"}>
                  <i>?</i><i>?</i>
                </ListGroupItem >
              </ListGroup>
            </Col>
          )
        })}
      </Row>

    </div>
  )
}
export default Items;
