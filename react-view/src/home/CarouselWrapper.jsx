import React, {useState} from "react";
import {
  Button,
  Carousel,
  CarouselCaption,
  CarouselControl,
  CarouselIndicators,
  CarouselItem,
  ListGroup
} from "reactstrap";
import button from "bootstrap/js/src/button";
import Buttons from "../admin/items/Buttons";

const items = [
  {
    src: '/images/game.jpg',
    altText: 'Slide 1',
    caption: 'Slide 1',
    key: 1,
  },
  {
    src: '/images/logo.jpg',
    altText: 'Slide 2',
    caption: 'Slide 2',
    key: 2,
  },
  {
    src: '/images/1.webp',
    altText: 'Slide 3',
    caption: 'Slide 3',
    key: 3,
  },
  {
    src: '/images/2.webp',
    altText: 'Slide 4',
    caption: 'Slide 4',
    key: 4,
  },
  {
    src: '/images/banner1.jpg',
    altText: 'Slide 5',
    caption: 'Slide 5',
    key: 5,
  },
  {
    src: '/images/banner2.jpg',
    altText: 'Slide 6',
    caption: 'Slide 6',
    key: 6,
  },
  {
    src: '/images/1234 (1).jpg',
    altText: 'Slide 7',
    caption: 'Slide 7',
    key: 7,
  },
];

const CarouselWrapper = (args) => {

  const [activeIndex, setActiveIndex] = useState(0);
  const [animating, setAnimating] = useState(false);

  const next = () => {
    if (animating) return;
    const nextIndex = activeIndex === items.length - 1 ? 0 : activeIndex + 1;
    setActiveIndex(nextIndex);
  };

  const previous = () => {
    if (animating) return;
    const nextIndex = activeIndex === 0 ? items.length - 1 : activeIndex - 1;
    setActiveIndex(nextIndex);
  };

  const goToIndex = (newIndex) => {
    console.log(newIndex);
    if (animating) return;
    setActiveIndex(newIndex);
  };

  const slides = items.map((item) => {
    return (
      <CarouselItem
        onExiting={() => setAnimating(true)}
        onExited={() => setAnimating(false)}
        key={item.src}
      >
        <div className={"d-flex justify-content-center align-content-center"} style={{height: "400px"}}>
          <img src={item.src} alt={item.altText}/>
        </div>
        <CarouselCaption
          captionText={item.caption}
          captionHeader={item.caption}
        />
      </CarouselItem>
    );
  });

  return (
    <div>
      <Carousel
        activeIndex={activeIndex}
        next={next}
        previous={previous}
        {...args}
        slide={false} fade={true}
      >
        <CarouselIndicators
          items={items}
          activeIndex={activeIndex}
          onClickHandler={goToIndex}
        />
        {slides}
      </Carousel>
      <ListGroup horizontal={true}>
        {
          [0, 1, 2, 3, 4, 5, 6].map((elem) => {
            return <Button className={"bg-white text-black rounded-0 flex-grow-1"} style={{height: "50px", borderColor: "#e7e7e7"}} onClick={() => goToIndex(elem)} key={elem}>파이널판타지 XVI</Button>
          })
        }

        {/*<Button className={"bg-white text-black rounded-0 flex-grow-1"} style={{height: "50px", borderColor: "#e7e7e7"}}>MLB The Show 23</Button>*/}
        {/*<Button className={"bg-white text-black rounded-0 flex-grow-1"} style={{height: "50px", borderColor: "#e7e7e7"}}>PSVR2</Button>*/}
        {/*<Button className={"bg-white text-black rounded-0 flex-grow-1"} style={{height: "50px", borderColor: "#e7e7e7"}}>PS5 듀얼센스 번들</Button>*/}
        {/*<Button className={"bg-white text-black rounded-0 flex-grow-1"} style={{height: "50px", borderColor: "#e7e7e7"}}>루프 에이트</Button>*/}
        {/*<Button className={"bg-white text-black rounded-0 flex-grow-1"} style={{height: "50px", borderColor: "#e7e7e7"}}>세계수의 미궁 HD 리마스터</Button>*/}
        {/*<Button className={"bg-white text-black rounded-0 flex-grow-1"} style={{height: "50px", borderColor: "#e7e7e7"}}>디아블로 IV출시</Button>*/}
      </ListGroup>
    </div>

  );

  return (
    <div>
      <div>
        <img src={"/images/game.jpg"} className={"w-100"}/>
        <img src={"/images/logo.jpg"} className={"w-100"}/>
      </div>

    </div>
  );
}

export default CarouselWrapper;
