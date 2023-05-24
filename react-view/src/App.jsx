import logo from './logo.svg';
import './App.css';
import {BrowserRouter, Route, Router, Switch} from "react-router-dom";
import ItemsMain from "./items/ItemsMain";
import {Component} from "react";
import AdminMain from "./admin/AdminMain";

const App = () => {



  return (
    <BrowserRouter>
      <Switch>
        <Route path={"/admin/items"} exact component={ItemsMain}/>
        <Route path={"/admin"} exact component={AdminMain}/>
      </Switch>
    </BrowserRouter>
  );
}

export default App;
