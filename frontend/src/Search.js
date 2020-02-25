import React from "react";

const SearchForm = (props) => {
  return (
    <div>
      <form>
        <div>
          <input
            type="text"
            placeholder="Type keywords here..."
            value={props.value}
            onChange={props.onChange}
          />
        </div>
      </form>
    </div>
  );
}

export default SearchForm;