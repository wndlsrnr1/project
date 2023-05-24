import React, {memo, useContext, useEffect, useState} from "react";
import {Alert, Col, Form, FormGroup, Input, Label} from "reactstrap";
import {ItemsContext} from "./ItemsMain";
import {actionObj} from "./ItemsMain";


const initialSearchData = {
  brands: [],
  categories: [],
  subcategories: [],
  price1: null,
  price2: null,
  quantity1: null,
  quantity2: null,
  itemName: ""
};

const Search = memo(() => {

    const {dispatch, load, itemList, searchState} = useContext(ItemsContext);

    const [searchData, setSearchData] = useState(initialSearchData);

    const [errors, setErrors] = useState({});

    console.log(itemList);
    useEffect(() => {
      if (load) {
        return;
      }

      //데이터 처리하기
      async function fetchData() {
        const response1 = await fetch("/admin/items/get/brands");
        const response2 = await fetch("/admin/items/get/categories");
        if (!(response1.ok && response2.ok)) {
          return;
        }
        const brandJson = await response1.json();
        const categoriesJson = await response2.json();
        const data = {...initialSearchData};
        data["brands"] = brandJson;
        data["categories"] = categoriesJson;
        data["subcategories"] = [];
        setSearchData(data);
        console.log({brands: brandJson, categories: categoriesJson});
      }

      fetchData();

    }, [load])

    const onChangeInput = (name) => (event) => {
      const obj = {...searchData};
      obj[name] = event.target.value;
      console.log(obj);
      setSearchData(obj);
    }

    const onChangeCategory = (event) => {
      const categoryId = event.target.value;
      fetch("/admin/items/get/subcategory/" + categoryId)
        .then(response => {
          if (response.ok) {
            return response.json();
          }
          return {subcategoryList: []};
        }).then(data => {
        const obj = {...searchData, subcategories: data.subcategoryList}
        console.log(data);
        setSearchData(obj);
      })
    }

    const errorCheck = (formData) => {
      const errors = {hasError: false};
      Object.keys(formData).map((key, index) => {
        errors[key] = "";
      });

      const quantity1 = "quantity1";
      const quantity2 = "quantity2";
      if (formData.get(quantity1) && formData.get(quantity2)) {
        if (quantity1 > quantity2) {
          errors.hasError = true;
          errors["quantity1"] = "최소값이 최대값보다 더 클 수 없습니다.";
          errors["quantity2"] = "최대값이 최소값보다 더 작을 수 없습니다.";
        }
      }

      const price1 = formData.get("price1");
      const price2 = formData.get("price2");
      if (price1 && price2) {
        if (price1 > price2) {
          errors.hasError = true;
          errors["price1"] = "최소값이 최대값보다 더 클 수 없습니다.";
          errors["price2"] = "최대값이 최소값보다 더 작을 수 없습니다.";
        }
      }

      const itemName = formData.get("itemName");
      const regex = /[!@#$%^&*(),.?":{}|<>]/;
      if (regex.test(itemName)) {
        errors.hasError = true;
        errors["itemName"] = "특수 문자를 제외하고 입력해주세요";
      }

      return errors;
    }

    const onSubmitSearch = (event) => {
      event.preventDefault();
      const formData = new FormData(event.target);
      const createdError = errorCheck(formData);

      const searchStateNew = {page: 1};
      Array.from(formData.keys()).forEach((key, index) => {
        if (formData.get(key)) {
          searchStateNew[key] = formData.get(key);
        } else {
          searchStateNew[key] = "";
        }
      });

      if (createdError.hasError) {
        setErrors(createdError);
        return;
      }

      console.log(Array.from(formData.keys()));

      //검색 상태값 바꾸기
      dispatch({type: actionObj.changeSearchState, searchState: searchStateNew});
    }

    return (
      <div className="p-1">
        <Form onSubmit={onSubmitSearch}>
          <div className="container mb-2 p-0 d-flex justify-content-lg-between">
            <div style={{width: "32%"}} className="me-1">
              <select className="form-select" name="brandId1">
                {
                  searchData.brands.length !== 0 ?
                    [<option key={0} value={""}>브랜드</option>].concat(
                      searchData.brands.map((data, index) => {
                        return <option value={data.id} key={data.id}>{data.nameKor}({data.name})</option>
                      }))
                    : <option key={0} value={""}>데이터 없음</option>
                }
              </select>
            </div>
            <div style={{width: "32%"}} className="me-1">
              <select className="form-select" name="categoryId1" onChange={onChangeCategory}>
                {
                  searchData.categories.length !== 0 ?
                    [<option key={0} value={""}>카테고리</option>].concat(
                      searchData.categories.map((data, index) => {
                        return <option value={data.id} key={data.id}>{data.nameKor}({data.name})</option>
                      })
                    ) : <option key={0} value={""}>카테고리 없음</option>
                }
              </select>
            </div>
            <div style={{width: "32%"}} className="me-1">
              <select className="form-select" name="subcategoryId1">
                {
                  searchData.subcategories.length !== 0 ?
                    [<option key={0} value={""}>서브카테고리</option>].concat(
                      searchData.subcategories.map((data, index) => {
                        return <option value={data.id} key={data.id}>{data.nameKor}({data.name})</option>
                      })
                    ) : <option key={0} value={""}>서브 카테고리</option>
                }
              </select>
            </div>
          </div>

          <div className={"d-flex justify-content-lg-between mb-1"}>
            <div style={{width: "50%"}} className={"d-flex justify-content-between "}>
              <Label sm={2} className={"me-2 input-group-text p-0"}>수량 범위</Label>
              <Input type={"number"} className={"me-2 form-control"} name={"quantity1"}
                     value={searchData.quantity1} onChange={onChangeInput("quantity1")}/>
              <Input type={"number"} className={"pe-2"} name={"quantity2"} value={searchData.quantity2}
                     onChange={onChangeInput("quantity2")}/>
            </div>
            <div style={{width: "calc(50% - 5px)"}} className={"d-flex justify-content-between "}>
              <Label className={"me-2 input-group-text p-0"} sm={2}>가격 범위</Label>
              <Input type={"number"} className={"me-2"} name={"price1"} value={searchData.price1}
                     onChange={onChangeInput("price1")}/>
              <Input type={"number"} className={"me-2"} name={"price2"} value={searchData.price2}
                     onChange={onChangeInput("price2")}/>
            </div>
          </div>

          <div className="d-flex py-1">
            <div className="input-group pe-1">
              <span className="input-group-text">상품명</span>
              <input type="text" className="form-control" name={"itemName"} value={searchData.itemName}
                     onChange={onChangeInput("itemName")}/>
            </div>

            <div className="input-group" style={{width: "60px"}}>
              <button type="submit" className="btn btn-primary">검색</button>
            </div>

          </div>
        </Form>
      </div>
    );
  });
export default Search;