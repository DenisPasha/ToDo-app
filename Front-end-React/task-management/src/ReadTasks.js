import { prettyDOM } from "@testing-library/react";
import React, { useState, useEffect } from "react";

export default function ReadTasks() {
  const [data, setData] = useState([]);

  useEffect(() => {
    fetchDataFromBackend();
  }, []);

  function fetchDataFromBackend() {
    fetch("http://localhost:8080/api/list-of-tasks")
      .then((res) => {
        if (!res.ok) {
          throw new Error("Failed to fetch data");
        }
        return res.json();
      })
      .then((data) => {
        setData(data);
      })
      .catch((error) => {
        console.error(error);
      });
  }

  function createComponent() {
    return data.map((current, index) => (
      <div key={current.id} className="create-task-container">
        <div>
          <h3>Task No. {index + 1}</h3>
        </div>

        <div className="inputs">
          <form>
            <input className="bg-beige" type="text" name="title" value={current.title} disabled />
            <textarea className="bg-beige"
              type="text"
              name="description"
              value={current.description}
              disabled
            />
          </form>
        </div>
      </div>
    ));
  }

  return (
    <div>
      <div className="title-holder">
        <h1>Your Tasks</h1>
      </div>
      {createComponent()}
    </div>
  );
}
