import React, { useState } from "react";

export default function DeleteTask(props) {
  const [titleToDelete, setTitleToDelete] = useState("");
  const [deleted, setDeleted] = useState("");

  function handleFormData(event) {
    const { value } = event.target;

    setTitleToDelete(value);
  }

  function handleSubmit(event) {
    event.preventDefault();

    fetch(`http://localhost:8080/api/delete/${titleToDelete}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(titleToDelete),
    })
      .then((response) => {
        if (response.status === 204) {
          setDeleted("You have succsessfully deleted the note");
        } else if (response.status === 404) {
          setDeleted("Note does not exist please try again");
        }
      })
      .catch((error) => {
        console.error(error);
      });
  }

  return (
    <>
      <div className="title-holder">
        <h1>Delete task</h1>
      </div>

      <form onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="Title"
          onChange={handleFormData}
          name="title"
          value={titleToDelete}
        />

        <button className="submit-btn" onClick={handleSubmit}>
          {" "}
          Submit{" "}
        </button>
      </form>
      <div>
        {" "}
        <h3>{deleted}</h3>{" "}
      </div>
    </>
  );
}
