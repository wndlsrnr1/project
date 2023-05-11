import React, {memo} from "react";
import {Form} from "reactstrap";

const Search = memo(() => {

    return (
        <div className="p-1">
            <Form>
                <div className="container p-0 d-flex justify-content-lg-between">
                    <div style={{width: "20%"}} className="me-1">
                        <select className="form-select" name="">
                            <option >브랜드</option>
                            <option>One</option>
                            <option>Two</option>
                            <option>Three</option>
                        </select>
                    </div>
                    <div style={{width: "20%"}} className="me-1">
                        <select className="form-select" name="">
                            <option>카테고리</option>
                            <option>One</option>
                            <option>Two</option>
                            <option>Three</option>
                        </select>
                    </div>
                    <div style={{width: "20%"}} className="me-1">
                        <select className="form-select" name="">
                            <option>서브카테고리</option>
                            <option>One</option>
                            <option>Two</option>
                            <option>Three</option>
                        </select>
                    </div>
                    <div style={{width: "20%"}} className="me-1 dropdown">
                        <button className="w-100 btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton1"
                                data-bs-toggle="dropdown">
                            가격 범위
                        </button>
                        <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                            <li>
                                <div className="input-group p-1">
                                    <input type="text" className="form-control"/>
                                        <span className="input-group-text">₩ 이상</span>
                                </div>
                            </li>
                            <li>
                                <div className="input-group p-1">
                                    <input type="text" className="form-control"/>
                                        <span className="input-group-text">₩ 이하</span>
                                </div>
                            </li>
                        </ul>
                    </div>
                    <div style={{width: "20%"}} className="dropdown">
                        <button className="w-100 btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton2"
                                data-bs-toggle="dropdown" aria-expanded="false">
                            수량 범위
                        </button>
                        <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                            <li>
                                <div className="input-group p-1">
                                    <input type="text" className="form-control"/>
                                        <span className="input-group-text">개 이상</span>
                                </div>
                            </li>

                            <li>
                                <div className="input-group p-1">
                                    <input type="text" className="form-control"/>
                                        <span className="input-group-text">개 이하</span>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
                <div className="d-flex py-1">
                    <div className="input-group pe-1">
                        <span className="input-group-text">상품명</span>
                        <input type="text" className="form-control"/>
                    </div>

                    <div className="input-group" style={{width: "60px"}}>
                        <button type="button" className="btn btn-primary">검색</button>
                    </div>
                </div>
            </Form>
        </div>
    )
});


export default Search;