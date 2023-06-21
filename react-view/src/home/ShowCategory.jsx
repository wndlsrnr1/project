import {
  Badge,
  Col,
  DropdownItem,
  DropdownMenu,
  DropdownToggle,
  ListGroup, ListGroupItem, NavLink,
  Tooltip,
  UncontrolledDropdown
} from "reactstrap";
import React, {useEffect, useState} from "react";
import List from "reactstrap/es/List";
import {Link} from "react-router-dom";


const ShowCategory = () => {
  const [categoryToggle, setCategoryToggle] = useState({});
  const [loaded, setLoaded] = useState(false);
  const [category, setCategory] = useState([]);
  const [subCategory, setSubcategory] = useState([]);

  const toggle = (id) => {
    const newToggle = {...categoryToggle};
    newToggle[id] = !newToggle[id]
    console.log("categoryToggle", newToggle);
    setCategoryToggle(newToggle);
  }


  useEffect(() => {
    if (loaded) return;
    setLoaded(true);
    fetch("/items/categories").then(response => {
      if (!response.ok) return;
      return response.json();
    }).then(data => {
      setCategory(data.data);
      const newObj = {};
      Object.keys(data.data).forEach(elem => {
        newObj[elem.id] = false
      });
      console.log(newObj);
      setCategoryToggle(newObj);
    });

    fetch("/items/subcategories").then(response => {
      if (!response.ok) return;
      return response.json();
    }).then(data => {
      setSubcategory(data.data);
    });

  }, []);

  return (
    <>
      <UncontrolledDropdown className={"flex-grow-1"}>
        <DropdownToggle
          caret
          color="primary"
          className={"w-100 h-100 rounded-0 rounded-start"}
        >
          카테고리
        </DropdownToggle>

        <DropdownMenu dark>
          <DropdownItem header>
            상품
          </DropdownItem>
          <DropdownItem divider/>
          {
            category.map((data, index) => {
              return (
                <DropdownItem id={"category_" + data.id} key={data.id} tag={"a"} href={"/items/category/" + data.id}>
                  {data.nameKor + "(" + data.name + ")"}
                </DropdownItem>
              )
            })
          }
          {
            !Object.keys(subCategory) ? null : Object.keys(subCategory).map((data, index) => {
              return (
                <Tooltip isOpen={categoryToggle[data]} target={"category_" + data} List placement={"right"} autohide={false}
                         toggle={() => toggle(data)} style={{background: "transparent", padding: "5px"}} className={"p-0"}>
                  <ListGroup className={"w-100"}>
                    {
                      subCategory[data].map((sub, index) => {
                        console.log("/items/subcategory/" + sub.id);
                        return (
                          <ListGroupItem className={"text-start"}>
                            <Link to={"/items/subcategory/" + sub.id} className={"text-black text-decoration-none"}>{sub.nameKor}</Link>
                          </ListGroupItem>
                        )
                      })
                    }
                  </ListGroup>
                </Tooltip>
              )
            })
          }
        </DropdownMenu>
      </UncontrolledDropdown>

    </>

  )
}

export default ShowCategory;
