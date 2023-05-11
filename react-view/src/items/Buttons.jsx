import React, {memo} from "react";
import {Button, Col, Row} from "reactstrap";

const Buttons = memo(() => {

    const onClickDelete = () => {
        return null
    }

    const onClickAdd = () => {
        return null;
    }

    return (
        <div className={"d-flex justify-content-between mt-3"}>
            <Row>
                <Col>
                    <Button className={"btn btn-secondary float-end"} type={"button"}>삭제</Button>
                </Col>
            </Row>
            <Row>
                <Col>
                    <Button color={"primary"} className={"btn float-end"} type={"button"}>상품 등록</Button>
                </Col>
            </Row>
        </div>
    )
});

export default Buttons;