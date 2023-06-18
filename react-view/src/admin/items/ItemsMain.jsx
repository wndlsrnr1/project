import React, {createContext, useMemo, useReducer, useEffect} from "react";
import {Container, Row} from "reactstrap";
import Search from "./Search";
import ItemList from "./ItemList";
import Buttons from "./Buttons";
import Paging from "./Paging";
import AddForm from "./AddForm";
import itemList from "./ItemList";


export const ItemsContext = createContext({
  dispatch: () => {
  }
});

const initialSearchState = {
  brandId1: "",
  categoryId1: "",
  subcategoryId1: "",
  quantity1: "",
  quantity2: "",
  price1: "",
  price2: "",
  itemName: "",
  page: 1,

};


//액션 정의
export const actionObj = {
  initialPage: "initialPage",
  selectAllItems: "selectAllItems",
  removeInputElem: "removeInputElem",
  addInputElem: "addInputElem",
  warningToggle: "warningToggle",
  addToggle: "addToggle",
  changeSearchState: "changeSearchState",
  setTotal: "setTotal",
  setItemList: "setItemList",
  editToggle: "editToggle",
  setEditItemId: "setEditItemId",

}

//변수
const initState = {
  itemList: [],
  load: false,
  itemSelected: [],
  modal: false,
  addModal: false,
  searchState: initialSearchState,
  total: 0,
  editItemId: -1,
  editModal: false,
}

const reducer = (state, action) => {
  switch (action.type) {
    //itemList업데이트
    case actionObj.initialPage: {
      //reducer에서 ItemList 받아 오기
      const itemList = [...action.itemList]
      return {
        ...state, itemList: itemList, load: true
      }
    }

    //전체값 세팅
    case (actionObj.setTotal): {
      console.log(action.total);
      return {...state, total: action.total};
    }

    //delete시 모든 아이템 선택
    case actionObj.selectAllItems: {
      if (state.itemSelected.length !== state.itemList.length) {
        const itemSelected = [];
        state.itemList.forEach(elem => itemSelected.push(elem.id));
        return {
          ...state, itemSelected: itemSelected
        }
      }
      return {
        ...state, itemSelected: [],
      }
    }

    //delete시 하나의 아이템 삭제
    case actionObj.removeInputElem: {
      const itemSelected = [...state.itemSelected].filter(elem => elem !== action.id);
      return {
        ...state, itemSelected: itemSelected,
      }
    }

    //클릭식 지금 아이템 추가
    case actionObj.addInputElem: {
      const itemSelected = [...state.itemSelected, action.id];
      return {
        ...state, itemSelected: itemSelected
      }
    }

    //modal
    case actionObj.warningToggle: {
      return {
        ...state, modal: !action.modal
      }
    }

    //itemList 초기값을 제외하여 선택
    case actionObj.setItemList: {
      return {
        ...state, itemList: action.itemList,
      }
    }

    //modal 끄고 키기
    case actionObj.addToggle: {
      console.log(action.addModal);
      return {
        ...state, addModal: !action.addModal,
      }
    }

    //지금 검색'된' 값 변경하기
    case actionObj.changeSearchState: {
      return {
        ...state, searchState: {...action.searchState},
      }
    }

    case actionObj.setEditItemId: {
      return {
        ...state, editItemId: action.editItemId,
      }
    }

    case actionObj.editToggle: {
      return {
        ...state, editModal: !action.editModal
      }
    }

    default:
      return state;
  }
}


const ItemsMain = () => {
  const [state, dispatch] = useReducer(reducer, initState);

  const {itemList, load, itemSelected, modal, addModal, searchState, total, editItemId, editModal} = state;
  const values = {
    dispatch, itemList, load, itemSelected, modal, addModal, searchState, total, editItemId, editModal
  };
  //첫화면 렌더링

  return (
    <ItemsContext.Provider value={values}>
      <Container style={{maxWidth: "800px"}}>
        <Row className={"py-5 text-center"}>
          <h2>상품 목록</h2>
        </Row>
        <Search/>
        <hr className={"my-4"}/>
        <ItemList/>
        <div className={"d-flex justify-content-between mt-3"}>
          <Buttons/>
          <AddForm/>
        </div>
        <Paging/>
      </Container>
    </ItemsContext.Provider>

  )
}

export default ItemsMain;