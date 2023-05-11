import React, {createContext, useMemo, useReducer, useEffect} from "react";
import {Container} from "reactstrap";
import Search from "./Search";
import ItemList from "./ItemList";
import Buttons from "./Buttons";
import Paging from "./Paging";


export const ItemsContext = createContext({
  dispatch: () => {
  }
});

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

    default:
      return state;
  }
}

//액션 정의
export const actionObj = {
  initialPage: "initialPage",
  firstLoadOff: "firstLoadOff",
  selectAllItems: "selectAllItems",
  inputChecked: "inputChecked",
  removeInputElem: "removeInputElem",
  addInputElem: "addInputElem",
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
}

const ItemsMain = () => {
  const [state, dispatch] = useReducer(reducer, initState);

  const {itemList, load, itemSelected, brand, subcategory, category, price, quantity, nameKor} = state;
  const values = {
    dispatch, itemList, load, itemSelected, brand, subcategory, category, price, quantity, nameKor
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
        <Buttons/>
        <Paging/>
      </Container>
    </ItemsContext.Provider>

  )
}

export default ItemsMain;