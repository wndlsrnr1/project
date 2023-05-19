import React, {memo, useCallback, useContext, useEffect, useReducer, useRef} from "react";
import {Input, Table} from "reactstrap";
import {ItemsContext, actionObj} from "./ItemsMain";


const ItemList = memo(() => {
  const {dispatch, itemList, load, itemSelected, brand, subcategory, category, price, quantity, nameKor} = useContext(ItemsContext);

  //변수확인
  useEffect(() => {
  }, [itemSelected]);

  const onCLickSelectAll = () => {
    dispatch({type: actionObj.selectAllItems});
  }

  const isAllChecked = () => {
    return itemSelected.length !== 0 && itemSelected.length === itemList.length;
  }

  useEffect(() => {
    if (load) {
      return;
    }
    fetch("/admin/items").then((response) => response.json())
      .then(data => {
        dispatch({type: actionObj.initialPage, itemList: data});
      });
  }, [itemList]);

  const onClickInputs = (event) => {
    if (itemSelected.includes(parseInt(event.target.value))) {
      dispatch({type: actionObj.removeInputElem, id: parseInt(event.target.value)});
    } else {
      dispatch({type: actionObj.addInputElem, id: parseInt(event.target.value)});
    }
  }

  return (
    <div className={"text-center"}>
      <Table>
        <thead>
        <tr>
          <th><Input type={"checkbox"} onClick={onCLickSelectAll} checked={isAllChecked()}/></th>
          <th>ID</th>
          <th>상품명</th>
          <th>가격</th>
          <th>수량</th>
          <th>브랜드</th>
          <th>카테고리</th>
          <th>서브카테고리</th>
        </tr>
        </thead>
        <tbody>
        {
          //배열 만들기
          itemList.map((elem, index) => {
            return (
              <tr key={elem.id}>
                <td>
                  <Input name={"id"} id={`id_${elem.id}`} value={elem.id} type={"checkbox"} onClick={onClickInputs} checked={itemSelected.includes(parseInt(elem.id))}/>
                </td>
                <td>{elem.id}</td>
                <td>{elem.nameKor}</td>
                <td>{elem.price}</td>
                <td>{elem.quantity}</td>
                <td>{elem.brandNameKor}</td>
                <td>{elem.categoryNameKor}</td>
                <td>{elem.subcategoryNameKor}</td>
              </tr>
            )
          })
        }
        </tbody>
      </Table>
    </div>
  )
});

export default ItemList;