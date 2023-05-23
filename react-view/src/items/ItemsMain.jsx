import React, {createContext, useMemo, useReducer, useEffect} from "react";
import {Container} from "reactstrap";
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
  firstLoadOff: "firstLoadOff",
  selectAllItems: "selectAllItems",
  inputChecked: "inputChecked",
  removeInputElem: "removeInputElem",
  addInputElem: "addInputElem",
  warningToggle: "warningToggle",
  addToggle: "addToggle",
  updatePage: "updatePage",
  changeSearchState: "changeSearchState",
  setTotal: "setTotal",
  setSearchState: "setSearchState",
  setItemList: "setItemList",

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

}

const reducer = (state, action) => {
  switch (action.type) {
    case actionObj.initialPage: {
      //reducer에서 ItemList 받아 오기
      const itemList = [...action.itemList]
      return {
        ...state, itemList: itemList, load: true
      }
    }

    case (actionObj.setTotal): {
      console.log(action.total);
      return {...state, total: action.total};
    }

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

    case actionObj.removeInputElem: {
      const itemSelected = [...state.itemSelected].filter(elem => elem !== action.id);
      return {
        ...state, itemSelected: itemSelected,
      }
    }

    case actionObj.addInputElem: {
      const itemSelected = [...state.itemSelected, action.id];
      return {
        ...state, itemSelected: itemSelected
      }
    }

    case actionObj.warningToggle: {
      return {
      ...state, modal: !action.modal
      }
    }

    case actionObj.setItemList: {
      return {
        ...state, itemList: action.itemList,
      }
    }

    case actionObj.addToggle: {
      console.log(action.addModal);

      return {
        ...state, addModal: !action.addModal,
      }
    }

    case actionObj.changeSearchState: {
      return {
        ...state, searchState: {...action.searchState},
      }
    }



    default:
      return state;
  }
}


const ItemsMain = () => {
  const [state, dispatch] = useReducer(reducer, initState);

  const {itemList, load, itemSelected, brand, subcategory, category, price, quantity, nameKor, modal, addModal, searchState, total} = state;
  const values = {
    dispatch, itemList, load, itemSelected, brand, subcategory, category, price, quantity, nameKor, modal, addModal, searchState, total
  };
  //첫화면 렌더링

  return (
    <ItemsContext.Provider value={values}>
      <Container style={{maxWidth: "800px"}}>
        <div className={"py-5 text-center"}>
          <h2>상품 목록</h2>
        </div>
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