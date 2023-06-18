import './App.css';
import {BrowserRouter, Redirect, Route, Router, Switch} from "react-router-dom";
import ItemsMain from "./admin/items/ItemsMain";
import {Component, useEffect, useState} from "react";
import AdminMain from "./admin/AdminMain";
import {domain} from "./constant/Constant";
import HomeMain from "./home/HomeMain";

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
          <Route path={"/"} exact component={HomeMain}/>
          <Route path={"/admin/items"} exact component={ItemsMain}/>
          <Route path={"/admin"} exact component={AdminMain}/>
        </Switch>
      </BrowserRouter> : null
  );
}

export default App;
