import { client, extractErrorMessage } from "./client";

export const expenseApi = {
  async getAll({ category, familyMemberId } = {}) {
    try {
      const params = {};
      if (category) params.category = category;
      if (familyMemberId) params.familyMemberId = familyMemberId;
      const response = await client.get("/expenses", { params });
      return response.data;
    } catch (error) {
      throw new Error(extractErrorMessage(error));
    }
  },

  async create(expense) {
    try {
      const response = await client.post("/expenses", expense);
      return response.data;
    } catch (error) {
      throw new Error(extractErrorMessage(error));
    }
  },

  async update(id, expense) {
    try {
      const response = await client.put(`/expenses/${id}`, expense);
      return response.data;
    } catch (error) {
      throw new Error(extractErrorMessage(error));
    }
  },

  async remove(id) {
    try {
      await client.delete(`/expenses/${id}`);
    } catch (error) {
      throw new Error(extractErrorMessage(error));
    }
  },

  async getSummaryByCategory() {
    try {
      const response = await client.get("/expenses/summary/by-category");
      return response.data;
    } catch (error) {
      throw new Error(extractErrorMessage(error));
    }
  },

  async getSummaryByMonth() {
    try {
      const response = await client.get("/expenses/summary/by-month");
      return response.data;
    } catch (error) {
      throw new Error(extractErrorMessage(error));
    }
  },

  async getCategories() {
    try {
      const response = await client.get("/categories");
      return response.data;
    } catch (error) {
      throw new Error(extractErrorMessage(error));
    }
  },
};
