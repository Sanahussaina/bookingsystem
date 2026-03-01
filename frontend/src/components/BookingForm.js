
import React, { useState, useEffect } from 'react';
import { getRooms, createBooking } from '../services/api';

function BookingForm() {
  const [rooms, setRooms] = useState([]);
  const [selectedRoom, setSelectedRoom] = useState('');
  const [startTime, setStartTime] = useState('');
  const [endTime, setEndTime] = useState('');
  const [agenda, setAgenda] = useState('');
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  useEffect(() => {
    getRooms().then(response => setRooms(response.data));
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setSuccess('');

    const bookingData = {
      room: { id: selectedRoom },
      user: { id: 1 }, // Hardcoding user 1 for now
      startTime,
      endTime,
      agenda,
    };

    try {
      await createBooking(bookingData);
      setSuccess('Booking request sent successfully! It is now pending approval.');
    } catch (err) {
      setError(err.response?.data || 'An error occurred.');
    }
  };

  return (
    <div>
      <h2>Create a New Booking</h2>
      <form onSubmit={handleSubmit}>
        {/* Add form fields for room dropdown, datetime-local inputs, etc. */}
        {/* Example for room selection: */}
        <select value={selectedRoom} onChange={e => setSelectedRoom(e.target.value)} required>
          <option value="">Select a Room</option>
          {rooms.map(room => (
            <option key={room.id} value={room.id}>
              {room.roomName} (Capacity: {room.capacity})
            </option>
          ))}
        </select>
        {/* ... other fields ... */}
        <button type="submit">Submit Booking Request</button>
      </form>
      {error && <p style={{ color: 'red' }}>{error}</p>}
      {success && <p style={{ color: 'green' }}>{success}</p>}
    </div>
  );
}

export default BookingForm;
