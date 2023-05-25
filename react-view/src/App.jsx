import logo from './logo.svg';
import './App.css';
import {BrowserRouter, Redirect, Route, Router, Switch} from "react-router-dom";
import ItemsMain from "./items/ItemsMain";
import {Component, useEffect, useState} from "react";
import AdminMain from "./admin/AdminMain";
import RedirectLogin from "./authorization/RedirectLogin"
import {domain} from "./constant/Constant";

const App = () => {

  const [auth, setAuth] = useState(false);

  //서버에서 로드할건지 정보 전달
  useEffect(() => {
    const currentURL = window.location.href;
    const uri = new URL(currentURL).pathname;

    fetch("/auth?uri=" + uri)
      .then(response => {
        if (!response.ok) {
          window.location.href = domain + "/login?requestURI=" + uri;
          return
        }
        setAuth(true);
      });
  }, []);


  return (auth ?
      <BrowserRouter>
        <Switch>
          <Route path={"/admin/items"} exact component={ItemsMain}/>
          <Route path={"/admin"} exact component={AdminMain}/>
          <Redirect to={"/login"} exact componet={RedirectLogin}/>
        </Switch>
      </BrowserRouter> : null
  );
}

export default App;
