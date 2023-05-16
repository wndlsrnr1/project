import React, {createContext, useMemo, useReducer, useEffect} from "react";
import {Container} from "reactstrap";
import Search from "./Search";
import ItemList from "./ItemList";
import Buttons from "./Buttons";
import Paging from "./Paging";
import AddForm from "./AddForm";


export const ItemsContext = createContext({
  dispatch: () => {
  }
});


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

}

//변수
const initState = {
  itemList: [],
  load: false,
  itemSelected: [],
  brand: {},
  subcategory: {},
  category: {},
  price: {},
  quantity: {},
  nameKor: {},
  modal: false,
  addModal: false,
}

const reducer = (state, action) => {
  switch (action.type) {
    case actionObj.initialPage: {
      //reducer에서 ItemList 받아 오기
      const itemList = [...action.itemList]
      console.log(itemList)
      return {
        ...state, itemList: itemList, load: true
      }
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

    case actionObj.addToggle: {
      console.log(action.addModal);
      return {
        ...state, addModal: !action.addModal,
      }
    }

    default:
      return state;
  }
}


const ItemsMain = () => {
  const [state, dispatch] = useReducer(reducer, initState);

  const {itemList, load, itemSelected, brand, subcategory, category, price, quantity, nameKor, modal, addModal} = state;
  const values = {
    dispatch, itemList, load, itemSelected, brand, subcategory, category, price, quantity, nameKor, modal, addModal
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