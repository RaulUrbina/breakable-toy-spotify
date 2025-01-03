import "@testing-library/jest-dom";

jest.mock("@/services/ApiService", () => ({
  search: jest.fn(),
}));

describe("SearchComponent", () => {
  
});
