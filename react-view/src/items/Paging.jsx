import React, {memo, useContext, useEffect} from "react";
import {actionObj, ItemsContext} from "./ItemsMain";
import {Pagination, PaginationItem, PaginationLink} from "reactstrap";
import data from "bootstrap/js/src/dom/data";

const Paging = () => {


  const {dispatch, searchState, total} = useContext(ItemsContext);

  const {page} = searchState;

  const totalPage = (total) => Math.ceil(total / 15);
  const totalChunk = (total) => Math.ceil(Math.ceil(total / 15) / 5);
  const presentChunk = (page) => Math.floor((page - 1) / 5) + 1;
  const lestPage = (total, page) => {
    return new Array(total % 5).fill().map((elem, index) => {
      return page + index;
    });
  }

  const onCLickPage = (event) => {
    const page = parseInt(event.target.text);

    const searchStateNew = {...searchState, page: page};
    dispatch({type: actionObj.changeSearchState, searchState: searchStateNew});
  }

  const onCLickPageLast = (event) => {
    let pageNext = 0;
    if (event.target.text === "다음") {
      pageNext = presentChunk(page) * 5 + 1;
    }

    if (event.target.text === "이전") {
      pageNext = presentChunk(page) * 5 - 5;
    }

    const searchStateNew = {...searchState, page: pageNext};
    dispatch({type: actionObj.changeSearchState, searchState: searchStateNew});
  }

  return (
    <div className="d-flex justify-content-center mt-3">
      <Pagination aria-label="Page navigation example">
        {
          page > 5 ?
            <PaginationItem className="page-item">
              <PaginationLink className="page-link" href="#" onClick={onCLickPageLast}>이전</PaginationLink>
            </PaginationItem> : null
        }
        {
          presentChunk(page) < totalChunk(total) ?
            new Array(5).fill().map((elem, index) => {
              return <PaginationItem active={presentChunk(page) * 5 - 4 + index === page}><PaginationLink
                onClick={onCLickPage} href="#">{presentChunk(page) * 5 - 4 + index}</PaginationLink>
              </PaginationItem>;
            })
            : lestPage(total, page).map((elem, index) => {
              return <PaginationItem active={presentChunk(page) * 5 - 4 + index === page}><PaginationLink
                onClick={onCLickPage} href="#">{presentChunk(page) * 5 - 4 + index}</PaginationLink>
              </PaginationItem>
            })
        }
        {
          presentChunk(page) + 1 < totalChunk(total) ?
          <PaginationItem>
            <PaginationLink onClick={onCLickPageLast} href="#">다음</PaginationLink>
          </PaginationItem> : null
        }
      </Pagination>
    </div>
  );
};

export default Paging;