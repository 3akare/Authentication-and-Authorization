const Container = ({ children }) => {
  return (
    <div className="w-screen h-screen flex items-center justify-center flex-col">
      {children}
    </div>
  );
};

export default Container;
