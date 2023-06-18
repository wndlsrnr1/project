import React, {useState} from "react";
import {Carousel, CarouselCaption, CarouselControl, CarouselIndicators, CarouselItem} from "reactstrap";

const Advertise = () => {
  /*
  const items = [
    {
      src: 'https://picsum.photos/id/123/1200/400',
      altText: 'Slide 1',
      caption: 'Slide 1',
      key: 1,
    },
    {
      src: 'https://picsum.photos/id/456/1200/400',
      altText: 'Slide 2',
      caption: 'Slide 2',
      key: 2,
    },
    {
      src: 'https://picsum.photos/id/678/1200/400',
      altText: 'Slide 3',
      caption: 'Slide 3',
      key: 3,
    },
  ];
  {items.map((item) => {
    return (
      <CarouselItem
        onExiting={() => setAnimating(true)}
        onExited={() => setAnimating(false)}
        key={item.src}
      >
        <img src={item.src} alt={item.altText} />
      </CarouselItem>
    );
  })}
   */


  const [activeIndex, setActiveIndex] = useState(0);
  const [animating, setAnimating] = useState(false);

  const next = () => {
    if (animating) return;
    const nextIndex = activeIndex === 0 ? 1 : 0;
    setActiveIndex(nextIndex);
  };

  const previous = () => {
    if (animating) return;
    // const nextIndex = activeIndex === 0 ? items.length - 1 : activeIndex - 1;
    const nextIndex = activeIndex === 0 ? 1 : 0;
    setActiveIndex(nextIndex);
  };

  return (
    <Carousel
      activeIndex={activeIndex}
      next={next}
      previous={previous}
      className={"slide carousel-fade"}

    >
      <CarouselItem onExiting={() => setAnimating(true)} onExited={() => setAnimating(false)}>
        <a href={"/"}>
          <img src="/images/banner1.jpg" alt="example1" style={{marginTop: "8px"}}/>
        </a>
      </CarouselItem>
      <CarouselItem onExiting={() => setAnimating(true)} onExited={() => setAnimating(false)}>
        <a href={"/"}>
          <img src="/images/banner2.jpg" alt="example2" style={{marginTop: "8px"}}/>
        </a>
      </CarouselItem>
    </Carousel>
  );
}

export default Advertise;