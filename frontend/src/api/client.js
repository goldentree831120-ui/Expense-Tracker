import axios from "axios";

const BASE_URL = import.meta.env.VITE_API_BASE_URL || "http://localhost:8080/api";

export const client = axios.create({
  baseURL: BASE_URL,
  headers: { "Content-Type": "application/json" },
});

/**
 * Turns the backend's ApiError / validation error shape into a single
 * readable string so components can just show it, instead of every caller
 * re-deriving it from the axios error.
 */
export function extractErrorMessage(error) {
  const data = error?.response?.data;
  if (!data) return error.message || "Something went wrong";
  if (data.fieldErrors) {
    return Object.entries(data.fieldErrors)
      .map(([field, msg]) => `${field}: ${msg}`)
      .join(", ");
  }
  return data.message || "Something went wrong";
}
