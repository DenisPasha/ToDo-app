import React, { useState } from "react";

export default function CreateTask(props) {
  const [creatTaskFormData, setCreatTaskFormData] = useState({
    title: "",
    description: "",
  });
  const [isCreated, setIsCreated] = useState(false);

  function handleFormData(event) {
    const { name, value } = event.target;
    setCreatTaskFormData((prev) => {
      return {
        ...prev,
        [name]: value,
      };
    });
  }

  function responseFromServer(isItCreated) {
    setIsCreated(isItCreated);
  }
  function handleSubmit(event) {
    event.preventDefault();
    // send the object to backend

    fetch("http://localhost:8080/api/create-task", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(creatTaskFormData),
    })
      .then((response) => {
        if (response.status === 201) {
          responseFromServer(true);
        } else if (response.status === 400) {
          responseFromServer(false);
        }
      })
      .catch((error) => {
        // Handle any errors that occur during the request
        console.error(error);
      });
  }

  return (
    <div className="create-task-container">
      <div className="title-holder">
        <h1>Create task</h1>
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
      {isCreated && <h4> Note is created successfully </h4>}
    </div>
  );
}
