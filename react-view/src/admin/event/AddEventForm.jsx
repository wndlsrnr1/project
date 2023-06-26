import React, {useContext, useEffect, useState} from "react";
import {
  Alert,
  Button,
  Col,
  Container, Form, FormFeedback,
  FormGroup,
  Input, InputGroup, InputGroupText,
  Label,
  Modal,
  ModalBody,
  ModalFooter,
  ModalHeader
} from "reactstrap";
import {ModalContext} from "./Buttons";
import {AiOutlineSearch} from "@react-icons/all-files/ai/AiOutlineSearch";

const AddEventForm = () => {

  const {modalToggle, setModalToggle} = useContext(ModalContext);
  const [items, setItems] = useState([]);
  const [date, setDate] = useState("");
  const [images, setImages] = useState("");
  const [searchParam, setSearchParam] = useState("");
  const [selectedItem, setSelectedItem] = useState("");
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");
  const [selectedImage, setSelectedImage] = useState("");
  const [errors, setErrors] = useState({});

  function resetValues() {
    setItems([]);
    setImages("");
    setSearchParam("");
    setSelectedItem("");
    setStartDate("");
    setEndDate("");
    setSelectedImage("");
    setErrors({});
  }

  const onClickCancel = () => {
    setModalToggle(false);
    resetValues();
  }

  const onChangeRadio = (event) => {
    setSelectedImage(event.target.value);
    console.log(event.target.value);
  }
  //controlled Input 찾아보기
  const onChangeSearchInput = (event) => setSearchParam(event.target.value);

  const onChangeSelectedItem = (event) => {
    setSelectedItem(event.target.value);
  }

  const onChangeStartDate = (event) => setStartDate(event.target.value);
  const onChangeEndDate = (event) => setEndDate(event.target.value);

  //imageLoad
  useEffect(() => {
    if (!selectedItem) return
    fetch("/admin/items/attaches/" + selectedItem).then(
      response => response.json()
    ).then(data => {
      setImages(data.data.imageList);
      console.log(data.data.imageList);
    })
  }, [selectedItem]);

  const onSubmitSearch = (event) => {
    event.preventDefault();
    fetch("/admin/items/search?item_name=" + searchParam, {
      method: "get",
    }).then(response => {

      if (response.ok) {
        response.json()
          .then(data => {
            setItems(data.data);
            console.log(data.data);
          });
        return;
      }

      response.json()
        .then(data => {
          setItems(data.error);
        });
    });
  }

  const onSubmitAddEvent = (event) => {
    event.preventDefault();

    const requestObj = {};
    requestObj.item_id = selectedItem;
    requestObj.start_date = startDate;
    requestObj.end_date = endDate;
    requestObj.image_id = selectedImage;

    fetch("/admin/items/event", {
      method: "post",
      headers: {'Content-Type': 'application/json'},
      body: JSON.stringify(requestObj)

    }).then(response => {
      if (response.ok) {
        resetValues();
        setModalToggle(false);
        return;
      }
      //에러일때
      response.json().then(data => {
        setErrors(data.fieldErrors);
      });
    })
  }

  return (
    <Modal isOpen={modalToggle} size={"lg"}>
      <ModalHeader hideCloseButton={true} className={"text-xs"}>이벤트 추가</ModalHeader>
      <ModalBody className={"text-xxl-center"}>
        <Container className={"w-75"}>
          {/*search form*/}
          <Form onSubmit={onSubmitSearch}>
            <InputGroup className={"mb-3"}>
              <Input value={searchParam} placeholder="상품이름 검색" name={"item_name"} onChange={onChangeSearchInput}/>
              <Button className={"bg-primary"} type={"submit"}><AiOutlineSearch/></Button>
            </InputGroup>
          </Form>

          {/*add form*/}
          <Form className={""} onSubmit={onSubmitAddEvent}>
            {/*search result*/}
            <div className={"mb-3"}>
              <h6 className={"mb-3 text-start"}>
                상품 목록 (15개 까지 검색 됩니다)
              </h6>
              <Input multiple name="item_id" type="select" className={"text-center"} onChange={onChangeSelectedItem}
                     defaultValue={null}>
                {
                  items.length === 0 ? <option value={""}>검색어를 입력해주세요</option> :
                    items.map((item) => {
                      return (
                        <option value={item.id}
                                selected={item.id === selectedItem}>{item.name + "(" + item.nameKor + ")"}</option>
                      )
                    })
                }
              </Input>
            </div>
            {
              !errors?.itemId ? null :
                <div className={"invalid-feedback d-block text-start mb-2"}>
                  {errors.itemId}
                </div>
            }
            <h6 className={"text-start"}>행사일 선택</h6>
            <div className={"d-flex justify-content-around mb-3"}>
              <InputGroup className={"me-2"}>
                <Input style={{maxHeight: "38px"}} placeholder="날짜" type={"date"} value={startDate}
                       onChange={onChangeStartDate} name={"start_date"}/>
                <InputGroupText style={{maxHeight: "38px"}} className={""}>시작시간</InputGroupText>
                {
                  !errors?.startDate ? null :
                  <div className={"invalid-feedback d-block text-start mb-2"}>
                    {errors.startDate}
                  </div>
                }
              </InputGroup>
              <InputGroup className={""}>
                <Input style={{maxHeight: "38px"}} placeholder="날짜" type={"date"} value={endDate}
                       onChange={onChangeEndDate} name={"end_date"}/>
                <InputGroupText style={{maxHeight: "38px"}} className={""}>종료시간</InputGroupText>
                {
                  !errors?.endDate ? null :
                    <div className={"invalid-feedback d-block text-start mb-2"}>
                      {errors.endDate}
                    </div>
                }
              </InputGroup>
            </div>
            <h6 className={"text-start"}>이미지 선택</h6>

            {/*이미지칸*/}
            <div className={"d-flex justify-content-start"}>
              {/*이미지 한단위*/}
              {
                !images || images.length === 0 ? (<div><span>이미지가 없습니다</span></div>) : (
                  images.map((elem, index) => {
                    return (
                      <div>
                        <Label className={"d-block position-relative me-2"}>
                          <img alt={elem.name} src={"/admin/items/images/" + elem.uuid} className={"w-100"}/>
                          <Input value={elem.id} name={"imageId"} type="radio"
                                 className={"position-absolute float-start"} style={{zIndex: "2", right: "5px"}}
                                 checked={selectedImage == elem.id}
                                 onChange={onChangeRadio}
                          />
                        </Label>
                      </div>
                    )
                  })
                )
              }
            </div>
            <Button type={"submit"} className={"d-md-none"}/>
          </Form>
        </Container>
      </ModalBody>
      <ModalFooter>
        <Button className={"bg-primary"} onClick={onSubmitAddEvent}>확인</Button>
        <Button onClick={onClickCancel}>취소</Button>
      </ModalFooter>
    </Modal>
  )
}

export default AddEventForm;