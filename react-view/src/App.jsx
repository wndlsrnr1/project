import logo from './logo.svg';
import './App.css';
import {BrowserRouter, Route, Router, Switch} from "react-router-dom";
import ItemsMain from "./items/ItemsMain";
import {Component} from "react";

function App() {
  return (
    <BrowserRouter>
      <Switch>
        <Route path={"/admin/items"} exact component={ItemsMain}/>
      </Switch>
    </BrowserRouter>
  );
}

export default App;
