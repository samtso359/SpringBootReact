import './DataTable.css';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import Input from "@mui/material/Input";
import DeleteForeverIcon from '@mui/icons-material/DeleteForever';
import EditIcon from '@mui/icons-material/Edit';
import IconButton from '@mui/material/IconButton';
import DoneIcon from "@mui/icons-material/DoneAllTwoTone";
import RevertIcon from "@mui/icons-material/NotInterestedOutlined";
import React, { useEffect } from "react";
import ReactDOM from "react-dom";
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import Modal from '@mui/material/Modal';


const style = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: 400,
  bgcolor: 'background.paper',
  border: '2px solid #000',
  boxShadow: 24,
  p: 4,
};

const PRODUCT_ENDPOINT = "http://localhost:8080/api/inventory/product"

const CustomTableCell = ({ row, name, onChange }) => {
  const { isEditMode } = row;
  return (
    <TableCell align="left">
      {isEditMode ? (
        <Input
          value={row[name]}
          name={name}
          onChange={e => onChange(e, row)}
        />
      ) : (
        row[name]
      )}
    </TableCell>
  );
};
function DataTable(){

  const [rows, setRows] = React.useState([])
  const [open, setOpen] = React.useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  

  const [previous, setPrevious] = React.useState({});
  const [responseStatus, setResponseStatus] = React.useState({})

  const getData = ()=>{
    fetch(PRODUCT_ENDPOINT+'/all').then(async (response) => {
      const data = await response.json();
      // check for error response
      if (!response.ok) {
        // get error message from body or default to response statusText
        const error = (data && data.message) || response.statusText;
        setResponseStatus({ response: response.ok, errorMessage: error });
        return Promise.reject(error);
      }

      setRows(data);
    })
    .catch((error) => {
      setResponseStatus({ errorMessage: error.toString() });
      console.error("There was an error!", error);
    });
  }
  useEffect(() => {
    getData()}

  ,[])

  const onToggleEditMode = id => {
    setRows(state => {
      return rows.map(row => {
        if (row.id === id) {
          return { ...row, isEditMode: !row.isEditMode };
        }
        return row;
      });
    });
  };

  const onChange = (e, row) => {
    if (!previous[row.id]) {
      setPrevious(state => ({ ...state, [row.id]: row }));
    }
    const value = e.target.value;
    const name = e.target.name;
    const { id } = row;
    const newRows = rows.map(row => {
      if (row.id === id) {
        return { ...row, [name]: value };
      }
      return row;
    });

    setRows(newRows);
  };

  const onRevert = id => {
    // const newRows = rows.map(row => {
    //   if (row.id === id) {
    //     return previous[id] ? previous[id] : row;
    //   }
    //   return row;
    // });
   
    setRows(state => {
      return rows.map(row => {
        if (row.id === id && previous[id]) {
          return {...previous[id], isEditMode:false} ;
        }
        return {...row, isEditMode:false};
      });
    });
    // setRows(newRows);
    setPrevious(state => {
      delete state[id];
      return state;
    });
  };
  const onDelete = id =>{
    fetch(PRODUCT_ENDPOINT+'/'+id, {
      method: "DELETE"
    })
      .then((response) => {

        if (response.status !== 200) {
          throw new Error(response.statusText);
        }

        // return response.json();
      })
      .then(() => {
        getData()
        handleClose()
      })
      .catch((err) => {
        console.log("err occured in adding data ", err)
      });
  
  }
  const onConfirmEdit = row =>{
 
    fetch(PRODUCT_ENDPOINT+'/'+row.id, {
      method: "PUT",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json"
      },
      body: JSON.stringify(row)
    })
      .then((response) => {

        if (response.status !== 200) {
          throw new Error(response.statusText);
        }

        // return response.json();
      })
      .then(() => {
        getData()
        setPrevious({});
      })
      .catch((err) => {
        console.log("err occured in adding data ", err)
      });
  }
  const handleSubmit = e => {
    e.preventDefault();
    const data = Array.from(e.target.elements)
      .filter((input) => input.name)
      .reduce(
        (obj, input) => Object.assign(obj, { [input.name]: input.value }),
        {}
      );
   
      fetch(PRODUCT_ENDPOINT, {
        method: "POST",
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
      })
        .then((response) => {
  
          if (response.status !== 200) {
            throw new Error(response.statusText);
          }
  
          // return response.json();
        })
        .then(() => {
          getData()
          handleClose()
        })
        .catch((err) => {
          console.log("err occured in adding data ", err)
        });
    
  }

  return (
    <Paper>
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style}>
          <Typography id="modal-modal-title" variant="h6" component="h2">
            Insert New Product
          </Typography>
          <form action={PRODUCT_ENDPOINT} onSubmit={handleSubmit} method="POST">
            <div className="pt-0 mb-3">
              <input
                type="text"
                placeholder="Product Name"
                name="name"
                className="focus:outline-none focus:ring relative w-full px-3 py-3 text-sm text-gray-600 placeholder-gray-400 bg-white border-0 rounded shadow outline-none"
                required
              />
            </div>
            <div className="pt-0 mb-3">
              <input
                placeholder="Price"
                name="price"
                className="focus:outline-none focus:ring relative w-full px-3 py-3 text-sm text-gray-600 placeholder-gray-400 bg-white border-0 rounded shadow outline-none"
                required
              />
            </div>
            <div className="pt-0 mb-3">
              <input
                placeholder="Quantity"
                name="quantity"
                className="focus:outline-none focus:ring relative w-full px-3 py-3 text-sm text-gray-600 placeholder-gray-400 bg-white border-0 rounded shadow outline-none"
                required
              />
            </div>
            <button type="submit" >Confirm</button>
          </form>
   
        </Box>
      </Modal>
      <button className="add-button" onClick={handleOpen}>
        Add New +
      </button>
      <Table aria-label="caption table">
        <caption>A quick demo displaying all the products from Spring Boot backend database.</caption>
        <TableHead>
          <TableRow>
            <TableCell align="left" />
            <TableCell align="left">Product ID</TableCell>
            <TableCell align="left">Product Name</TableCell>
            <TableCell align="left">Price</TableCell>
            <TableCell align="left">Quantity</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {rows.map(row => (
            <TableRow key={row.id}>
              <TableCell>
                {row.isEditMode ? (
                  <>
                    <IconButton
                      aria-label="done"
                      onClick={() => onConfirmEdit(row)}
                    >
                      <DoneIcon />
                    </IconButton>
                    <IconButton
                      aria-label="revert"
                      onClick={() => onRevert(row.id)}
                    >
                      <RevertIcon />
                    </IconButton>
                    <IconButton
                      aria-label="delete"
                      onClick={() => onDelete(row.id)}
                    >
                      <DeleteForeverIcon />
                    </IconButton>
                  </>
                ) : (
                  <IconButton
                    aria-label="delete"
                    onClick={() => onToggleEditMode(row.id)}
                  >
                    <EditIcon />
                  </IconButton>
                )}
              </TableCell>
              <TableCell align="left">  
                  {row.id}
              </TableCell>
              <CustomTableCell {...{ row, name: "name", onChange }} />
              <CustomTableCell {...{ row, name: "price", onChange }} />
              <CustomTableCell {...{ row, name: "quantity", onChange }} />
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </Paper>
  );
}



export default DataTable;