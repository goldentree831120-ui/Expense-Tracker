import { client, extractErrorMessage } from "./client";

export const familyApi = {
  async getAll() {
    try {
      const response = await client.get("/family-members");
      return response.data;
    } catch (error) {
      throw new Error(extractErrorMessage(error));
    }
  },

  async create(member) {
    try {
      const response = await client.post("/family-members", member);
      return response.data;
    } catch (error) {
      throw new Error(extractErrorMessage(error));
    }
  },

  async update(id, member) {
    try {
      const response = await client.put(`/family-members/${id}`, member);
      return response.data;
    } catch (error) {
      throw new Error(extractErrorMessage(error));
    }
  },

  async remove(id) {
    try {
      await client.delete(`/family-members/${id}`);
    } catch (error) {
      throw new Error(extractErrorMessage(error));
    }
  },

  async getBudgetSummary() {
    try {
      const response = await client.get("/family-members/budget-summary");
      return response.data;
    } catch (error) {
      throw new Error(extractErrorMessage(error));
    }
  },
};
