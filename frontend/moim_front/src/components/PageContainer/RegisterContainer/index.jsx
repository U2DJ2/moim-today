function PageContainer({ children }) {
  return (
    <div className="flex min-h-screen w-full py-0 overflow-hidden relative gap-1 pl-28 bg-scarlet">
      <div>{children}</div>
    </div>
  );
}

export default PageContainer;
