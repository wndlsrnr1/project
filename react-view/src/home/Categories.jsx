import React, {useEffect, useState} from "react";
import {DropdownItem, DropdownMenu, DropdownToggle, ListGroup, ListGroupItem, UncontrolledDropdown} from "reactstrap";
import ShowCategory from "./ShowCategory";

const Categories = () => {

  const [loaded, setLoaded] = useState(false);
  const [brands, setBrands] = useState(null);

  useEffect(() => {
    if (loaded) return;
    fetch("/items/brands/most").then(response => {
      return response.json();
    }).then(data => {
      setBrands(data.data);
    });
    setLoaded(true);
  });

  return (
    <ListGroup horizontal className={"w-100 mb-4"}>
      <ShowCategory/>
      <ListGroupItem className={"bg-primary text-white flex-grow-1 text-center"} href="/items/reserved" tag="a">
        예약상품
      </ListGroupItem>
      <ListGroupItem className={"bg-primary text-white flex-grow-1 text-center"} href="/items/sale" tag="a">
        세일상품
      </ListGroupItem>
      <ListGroupItem className={"bg-primary text-white flex-grow-1 text-center"} href="/items/new" tag="a">
        신상품
      </ListGroupItem>
      <ListGroupItem className={"bg-primary text-white flex-grow-1 text-center"} href="/items/exhibition" tag="a">
        기획전
      </ListGroupItem>
      {
        !brands ? null : new Array(3).fill().map((none, index) => {
          const elem = brands[index];
          return (
            <ListGroupItem className={"bg-primary text-white flex-grow-1 text-center"} href={"/items/brand/" + elem.id}
                           tag="a" key={elem.id}>
              {elem.name}
            </ListGroupItem>
          )
        })
      }
    </ListGroup>
  );
}

export default Categories;