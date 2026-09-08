import { client, extractErrorMessage } from "./client";

export const tripApi = {
  async getAll() {
    try {
      const response = await client.get("/trips");
      return response.data;
    } catch (error) {
      throw new Error(extractErrorMessage(error));
    }
  },

  async getById(id) {
    try {
      const response = await client.get(`/trips/${id}`);
      return response.data;
    } catch (error) {
      throw new Error(extractErrorMessage(error));
    }
  },

  async create(trip) {
    try {
      const response = await client.post("/trips", trip);
      return response.data;
    } catch (error) {
      throw new Error(extractErrorMessage(error));
    }
  },

  async remove(id) {
    try {
      await client.delete(`/trips/${id}`);
    } catch (error) {
      throw new Error(extractErrorMessage(error));
    }
  },

  async getExpenses(tripId) {
    try {
      const response = await client.get(`/trips/${tripId}/expenses`);
      return response.data;
    } catch (error) {
      throw new Error(extractErrorMessage(error));
    }
  },

  async addExpense(tripId, expense) {
    try {
      const response = await client.post(`/trips/${tripId}/expenses`, expense);
      return response.data;
    } catch (error) {
      throw new Error(extractErrorMessage(error));
    }
  },

  async removeExpense(tripId, expenseId) {
    try {
      await client.delete(`/trips/${tripId}/expenses/${expenseId}`);
    } catch (error) {
      throw new Error(extractErrorMessage(error));
    }
  },

  async getWeeklySummary(tripId) {
    try {
      const response = await client.get(`/trips/${tripId}/summary/weekly`);
      return response.data;
    } catch (error) {
      throw new Error(extractErrorMessage(error));
    }
  },
};
