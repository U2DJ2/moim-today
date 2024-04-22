function PageContainer({ children }) {
  return (
    <div className="max-w-screen-xl min-h-[800px] mx-auto px-2 md:px-5 mb-24">
      <div>{children}</div>
    </div>
  );
}

export default PageContainer;
