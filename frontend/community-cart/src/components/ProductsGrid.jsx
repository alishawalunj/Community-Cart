import React from "react";
import ProductCard from "./ProductCard";

const ProductsGrid = ({ productList, mode }) => {
  console.log("Rendering ProductsGrid with mode:", mode);

  return (
    <div className="px-3 w-full flex justify-center">
      <div
        style={{
          display: "grid",
          gridTemplateColumns: "repeat(auto-fit, minmax(320px, 1fr))",
          gap: "40px",
          justifyItems: "center",
          width: "100%",
          maxWidth: "1400px",
        }}
      >
        {productList.map((product) => (
          <ProductCard key={product.productId} product={product} mode={mode} />
        ))}
      </div>
    </div>
  );
};

export default ProductsGrid;
