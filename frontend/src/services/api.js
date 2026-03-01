
import axios from 'axios';
const API_URL = 'http://localhost:8080/api'; // Our backend URL

export const createBooking = (bookingData) => {
  return axios.post(`${API_URL}/bookings`, bookingData);
};

export const getRooms = () => {
  return axios.get(`${API_URL}/rooms`);
};

// Add other functions for getting bookings, etc.
