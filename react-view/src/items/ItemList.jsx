import React, {memo, useCallback, useContext, useEffect, useReducer, useRef} from "react";
import {Input, NavLink, Table} from "reactstrap";
import {ItemsContext, actionObj} from "./ItemsMain";
import data from "bootstrap/js/src/dom/data";
import EditForm from "./EditForm";


const ItemList = memo(() => {
  const {
    dispatch,
    itemList,
    load,
    itemSelected,
    modal,
    addModal,
    searchState,
    total,
    editItemId,
    editModal
  } = useContext(ItemsContext);

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
    let formData = new FormData();
    Object.keys(searchState).forEach(key => {
      formData.append(key, searchState[key]);
    })

    fetch("/admin/items", {
      method: "post",
      body: formData,
    }).then((response) => response.json())
      .then(data => {
        //검색 상태값 바꾸기
        console.log(data);
        dispatch({type: actionObj.initialPage, itemList: [...data.itemList]});
        dispatch({type: actionObj.setTotal, total: data.total});
      });
  }, [searchState]);

  const onClickInputs = (event) => {
    if (itemSelected.includes(parseInt(event.target.value))) {
      dispatch({type: actionObj.removeInputElem, id: parseInt(event.target.value)});
    } else {
      dispatch({type: actionObj.addInputElem, id: parseInt(event.target.value)});
    }
  }

  const onClickEditItem = (event) => {
    event.preventDefault();
    const itemIdName = event.target.id;
    const itemId = parseInt(itemIdName.split("_")[1]);
    console.log(itemId);
    dispatch({type: actionObj.setEditItemId, editItemId: itemId});
    dispatch({type: actionObj.editToggle, editModal: editModal});
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
          itemList.length !== 0 ? itemList.map((elem, index) => {
            return (
              <tr key={elem.id}>
                <td>
                  <Input name={"id"} id={`id_${elem.id}`} value={elem.id} type={"checkbox"} onClick={onClickInputs}
                         checked={itemSelected.includes(parseInt(elem.id))}/>
                </td>
                <td>{elem.id}</td>
                <td><NavLink onClick={onClickEditItem} className={"p-0"} id={"item_" + elem.id}>{elem.nameKor}</NavLink>
                </td>
                <td>{elem.price}</td>
                <td>{elem.quantity}</td>
                <td>{elem.brandNameKor}</td>
                <td>{elem.categoryNameKor}</td>
                <td>{elem.subcategoryNameKor}</td>
              </tr>
            )
          }) : <>
            <tr>
              <td colSpan={12}>검색 결과가 없습니다</td>
            </tr>
          </>
        }
        </tbody>
        <EditForm/>
      </Table>
    </div>
  )
});

export default ItemList;