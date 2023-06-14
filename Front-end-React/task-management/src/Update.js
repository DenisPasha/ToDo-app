import React, { useState } from "react";
import CreateTask from "./CreateTask";

export default function Update() {
  const [creatTaskFormData, setCreatTaskFormData] = useState({
    title: "",
    description: "",
  });
  const [updated, setUpdated] = useState("");

  function handleSubmit(event) {
    event.preventDefault();
    // send the object to backend

    fetch(`http://localhost:8080/api/edit/${creatTaskFormData.title}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(creatTaskFormData),
    })
      .then((response) => {
        if (response.status === 200) {
          setUpdated("You have succsessfully updated the note");
        } else if (response.status === 404) {
          setUpdated("Cannot update non existing note");
        }
      })
      .catch((error) => {
        // Handle any errors that occur during the request
        console.error(error);
      });
  }

  function handleFormData(event) {
    const { name, value } = event.target;
    setCreatTaskFormData((prev) => {
      return {
        ...prev,
        [name]: value,
      };
    });
  }

  return (
    <div className="create-task-container">
      <div className="title-holder">
        <h1>Update task</h1>
      </div>

      <div className="inputs">
        <form onSubmit={handleSubmit}>
          <input
            type="text"
            placeholder="Title"
            onChange={handleFormData}
            name="title"
            value={creatTaskFormData.title}
          />

          <textarea
            type="text"
            placeholder="Description"
            onChange={handleFormData}
            name="description"
            value={creatTaskFormData.description}
          />
          <button className="submit-btn" onClick={handleSubmit}>
            {" "}
            Submit{" "}
          </button>
        </form>
      </div>
      <div>
        {" "}
        <h3>{updated}</h3>{" "}
      </div>
    </div>
  );
}
