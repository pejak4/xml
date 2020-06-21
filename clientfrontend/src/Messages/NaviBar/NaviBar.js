import React from 'react';
import './NaviBar.css';


class NaviBar extends React.PureComponent{

    state = {tip: ''};

    callBack(str){
        this.props.ret(str);
    }


    render(){
        return (
            <div>
                <nav className="navbar">
                    <h1 className="title">
                        <span className="logo">Rental</span>Messages
                    </h1>

                    <ul>
                        <li><button onClick={()=>{ this.callBack('INBOX');}}>Inbox </button></li>
                        <li><button onClick={()=>{this.callBack('SENT');}}>Sent</button></li>
                    </ul>
                </nav>
            </div>
        );
    }



}

export default NaviBar;