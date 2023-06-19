import './App.css';
import {BrowserRouter, Redirect, Route, Router, Switch} from "react-router-dom";
import ItemsMain from "./admin/items/ItemsMain";
import {Component, createContext, useEffect, useReducer, useState} from "react";
import AdminMain from "./admin/AdminMain";
import {domain} from "./constant/Constant";
import HomeMain from "./home/HomeMain";


export const UniversalContext = createContext({
  dispatch: () => {
  }
});

const logAction = {
  setLogIn: "setLogIn",
  logout: "logout",
}

const universalState = {
  isLoggedIn: false,
}

const reducer = (state, action) => {
  switch (action.type) {
    case logAction.setLogIn: {
      return {...state, isLoggedIn: true};
    }

    case logAction.logout: {
      return {...state, isLoggedIn: false};
    }

    default: {
      return {...state};
    }
  }
}

const App = () => {

  const [state, dispatch] = useReducer(reducer, universalState);

  const [auth, setAuth] = useState(false);

  const {isLoggedIn} = state;

  const values = {isLoggedIn, dispatch};


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

  useEffect(() => {
    console.log(isLoggedIn);
    fetch("/isLogin", {
      method: "post"
    }).then((response) => {
      if (response.ok) {
        console.log("logAction.setLogIn")
        dispatch({type:logAction.setLogIn});
      }
    });
  }, [isLoggedIn]);

  // useEffect(() => {
  //   if (!auth) return;
  //   const currentURL = window.location.href;
  //   const uri = new URL(currentURL).pathname;
  //
  //   fetch(uri, {
  //     method: "GET"
  //   })
  //     .then(response => {
  //       console.log(response.status);
  //       console.log("response", response.ok);
  //       if (!response.ok) window.location.href = domain + uri;;
  //       console.log("response", response.ok);
  //
  //     });
  // }, [auth]);


  return (auth ?
      <UniversalContext.Provider value={values}>
        <BrowserRouter>
          <Switch>
            <Route path={"/"} exact component={HomeMain}/>
            <Route path={"/admin/items"} exact component={ItemsMain}/>
            <Route path={"/admin"} exact component={AdminMain}/>
          </Switch>
        </BrowserRouter>
      </UniversalContext.Provider> : null

  );
}

export default App;
