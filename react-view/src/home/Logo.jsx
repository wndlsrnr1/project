import React from "react";

const Logo = () => {

  return (
    <h3 style={{height: "80px"}}>
      <a href={"/"} className={"d-block"} style={{height: "100%"}}>
        <img alt={"logo"} src={"/images/logo.jpg"} style={{height: "100%", marginTop: "8px"}}/>
      </a>
    </h3>
  )
}

export default Logo;