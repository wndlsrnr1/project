import React, {createContext, useReducer} from "react";
import {Col, Container, Row} from "reactstrap";
import Header from "./Header";
import Logo from "./Logo";
import Advertise from "./Advertise";
import Categories from "./Categories";
import Items from "./Items";
import Footer from "./Footer";
import RecentView from "./RecentView";
import HomeSearch from "./HomeSearch";
import CarouselWrapper from "./CarouselWrapper";

export const HomeContext = createContext({
  dispatch: () => {
  },
})

const initState = {
  something: [],
};

const reducer = (state, action) => {

}


const HomeMain = () => {
  const [state, dispatch] = useReducer(reducer, initState);
  const {something} = state;
  const values = {something};

  return (
    <React.Fragment>
      <Header/>
      <div className={"d-flex justify-content-lg-between align-content-end p-3 border-bottom"}>
        <Logo/>
        <HomeSearch/>
        <Advertise/>
      </div>

      <Container className={"w-75 p-3"}>
        <Categories/>
        <CarouselWrapper/>
        <Items/>
      </Container>
      <Footer/>
      <RecentView/>
    </React.Fragment>
  );
}

export default HomeMain;